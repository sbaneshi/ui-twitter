package ir.ac.ui.connection.http;

import akka.actor.typed.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.*;
import akka.japi.function.Function;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;
import ir.ac.ui.akka.model.ErrorResponse;
import ir.ac.ui.akka.model.Message;
import ir.ac.ui.common.service.UserActorApi;
import ir.ac.ui.connection.config.HttpConnectionProperties;
import ir.ac.ui.connection.http.api.Follow;
import ir.ac.ui.connection.http.api.GetHistory;
import ir.ac.ui.connection.http.api.SendTweet;
import ir.ac.ui.connection.http.api.Signup;
import ir.ac.ui.user.actor.api.UserRequest;
import ir.ac.ui.user.actor.api.signup.SignupRequest;
import ir.ac.ui.user.actor.api.tweeting.FollowRequest;
import ir.ac.ui.user.actor.api.tweeting.GetHistoryRequest;
import ir.ac.ui.user.actor.api.tweeting.SendTweetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpConnectionEndpoint {

    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }

    private static final SecureRandom random = new SecureRandom();
    private static final ObjectMapper objectMapper = createObjectMapper();
    private static final Map<Integer, Class<?>> messageRegistry = ImmutableMap.of(
            1, Signup.class,
            2, Follow.class,
            3, SendTweet.class,
            4, GetHistory.class
    );

    private final ActorSystem<Message> actorSystem;
    private final HttpConnectionProperties props;
    private final UserActorApi userActorApi;

    @PostConstruct
    public void initHttpRoute() {
        log.info("Setting up http server...");
        Http http = Http.get(actorSystem);

        http.newServerAt(props.getServer(), props.getPort())
                .bindFlow(Flow.fromFunction((Function<HttpRequest, CompletableFuture<?>>) req -> {
                            int reqType = req.getHeader("Request-Type")
                                    .map(HttpHeader::value)
                                    .map(Integer::new)
                                    .orElse(0);
                            Class<?> reqClass = messageRegistry.get(reqType);
                            if (reqClass == null) {
                                return CompletableFuture.completedFuture(ErrorResponse.validation("INVALID_REQUEST_TYPE"));
                            }
                            return Jackson.unmarshaller(reqClass).unmarshal(req.entity(), actorSystem)
                                    .thenApply(x -> resolveUserRequestBuilder(req, x))
                                    .thenCompose(this::askUserRequest)
                                    .exceptionally(e -> processException(req, e))
                                    .toCompletableFuture();
                        })
                        .mapAsyncUnordered(props.getConcurrency(), x -> x)
                        .map(x -> {
                            if (x instanceof ErrorResponse) {
                                ErrorResponse errorResponse = (ErrorResponse) x;
                                return HttpResponse.create()
                                        .withStatus(errorResponse.getErrorCode())
                                        .withEntity(HttpEntities.create(ContentTypes.TEXT_PLAIN_UTF8, errorResponse.getErrorMessage()));
                            }

                            return HttpResponse.create()
                                    .withStatus(200)
                                    .withEntity(HttpEntities.create(
                                            ContentTypes.APPLICATION_JSON,
                                            objectMapper.writeValueAsBytes(x)));
                        }));
    }

    private UserRequest.UserRequestBuilder<?, ?> resolveUserRequestBuilder(HttpRequest req, Object x) {
        long userId = getUserId(req);
        UserRequest.UserRequestBuilder<?, ?> requestBuilder;
        if (x instanceof Signup) {
            Signup signup = (Signup) x;
            requestBuilder = SignupRequest.builder()
                    .userId(Math.abs(random.nextLong()))
                    .phone(signup.getPhone())
                    .name(signup.getName());
        } else if (x instanceof Follow) {
            Follow follow = (Follow) x;
            requestBuilder = FollowRequest.builder()
                    .userId(userId)
                    .targetUserId(follow.getTargetUserId());
        } else if (x instanceof SendTweet) {
            SendTweet sendTweet = (SendTweet) x;
            requestBuilder = SendTweetRequest.builder()
                    .userId(userId)
                    .message(sendTweet.getMessage());
        } else if (x instanceof GetHistory) {
            GetHistory getHistory = (GetHistory) x;
            requestBuilder = GetHistoryRequest.builder()
                    .userId(userId);
        } else {
            throw new IllegalArgumentException("INVALID_MESSAGE_BODY");
        }
        return requestBuilder;
    }

    private CompletableFuture<Object> askUserRequest(UserRequest.UserRequestBuilder<?, ?> x) {
        return userActorApi.ask(r -> x
                .replyTo(r)
                .build());
    }

    private ErrorResponse processException(HttpRequest req, Throwable e) {
        if (e instanceof TimeoutException) {
            log.warn("Ask Timout while processing Request=[{}] for User[{}]", req, getUserId(req));
            return ErrorResponse.unexpected("ASK_TIMEOUT");
        }
        return ErrorResponse.unexpected(e.getMessage());
    }

    private Long getUserId(HttpRequest req) {
        return req.getHeader("User-Id")
                .map(HttpHeader::value)
                .map(Long::new)
                .orElse(0L);
    }
}
