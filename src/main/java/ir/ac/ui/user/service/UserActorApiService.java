package ir.ac.ui.user.service;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import akka.japi.function.Function;
import ir.ac.ui.akka.model.Message;
import ir.ac.ui.common.service.UserActorApi;
import ir.ac.ui.user.actor.api.UserMessage;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UserActorApiService implements UserActorApi {

    private final ActorSystem<Message> actorSystem;
    private final ActorRef<UserMessage> shardRegion;

    @Override
    public void send(UserMessage message) {
        shardRegion.tell(message);
    }

    @Override
    public <T> CompletableFuture<T> ask(Function<ActorRef<T>, UserMessage> messageCreator) {
        return AskPattern.ask(shardRegion,
                        messageCreator, Duration.ofSeconds(10),
                        actorSystem.scheduler())
                .toCompletableFuture();
    }
}
