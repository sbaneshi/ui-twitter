package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.init.BecomeNormal;
import ir.ac.ui.user.actor.api.init.BecomeSignup;
import ir.ac.ui.user.actor.api.init.InitUserRequest;
import ir.ac.ui.user.data.user.UserEntity;
import ir.ac.ui.user.data.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitUserMessageHandler implements UserMessageHandler<InitUserRequest> {

    private final UserRepository userRepository;

    @Override
    public void handle(HandlerContext<UserActorData> context, InitUserRequest request) {
        final long userId = context.getActorData().getUserId();
        Optional<UserEntity> user = userRepository.findById(userId);
        user.ifPresent(x -> context.getActorData().getFollowers().addAll(x.getFollowers()));

        if (user.isPresent()) {
            log.info("User[{}] initialized.", userId);
            context.getSelf().tell(BecomeNormal.INSTANCE);
        } else {
            log.info("User[{}] not registered; waiting for signup...", userId);
            context.getSelf().tell(BecomeSignup.INSTANCE);
        }
    }

    @Override
    public Class<InitUserRequest> messageClass() {
        return InitUserRequest.class;
    }
}
