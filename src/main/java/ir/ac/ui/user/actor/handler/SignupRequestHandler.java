package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.init.BecomeNormal;
import ir.ac.ui.user.actor.api.signup.SignupRequest;
import ir.ac.ui.user.actor.api.signup.SignupResponse;
import ir.ac.ui.user.data.user.UserEntity;
import ir.ac.ui.user.data.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignupRequestHandler implements UserMessageHandler<SignupRequest> {

    private final UserRepository userRepository;

    @Override
    public void handle(HandlerContext<UserActorData> context, SignupRequest request) {
        final long userId = context.getActorData().getUserId();
        UserEntity entity = createEntity(userId, request);
        userRepository.save(entity);

        context.getSelf().tell(BecomeNormal.INSTANCE);
        request.safeReply(SignupResponse.builder()
                .userId(userId)
                .build());
        log.info("User[{}] signed up successfully.", userId);
    }

    private UserEntity createEntity(long userId, SignupRequest request) {
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setName(request.getName());
        entity.setPhone(request.getPhone());
        entity.setFollowers(new ArrayList<>());
        return entity;
    }

    @Override
    public Class<SignupRequest> messageClass() {
        return SignupRequest.class;
    }
}
