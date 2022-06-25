package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.akka.model.DefaultResponse;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.tweeting.FollowRequest;
import ir.ac.ui.user.data.user.UserEntity;
import ir.ac.ui.user.data.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowRequestHandler implements UserMessageHandler<FollowRequest> {

    private final UserRepository userRepository;

    @Override
    public void handle(HandlerContext<UserActorData> context, FollowRequest request) {
        UserActorData actorData = context.getActorData();
        if (actorData.getFollowers().contains(request.getTargetUserId())) {
            request.safeReply(DefaultResponse.SUCCESSFUL);
        }

        UserEntity entity = userRepository.findById(actorData.getUserId())
                .orElseThrow(IllegalArgumentException::new);
        entity.getFollowers().add(request.getTargetUserId());
        userRepository.saveAndFlush(entity);

        actorData.getFollowers().add(request.getTargetUserId());
        request.safeReply(DefaultResponse.SUCCESSFUL);
    }

    @Override
    public Class<FollowRequest> messageClass() {
        return FollowRequest.class;
    }
}
