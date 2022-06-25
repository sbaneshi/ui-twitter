package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.common.service.UserActorApi;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.internal.GetTweetsRequest;
import ir.ac.ui.user.actor.api.tweeting.GetHistoryRequest;
import ir.ac.ui.user.actor.api.tweeting.GetHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetHistoryRequestHandler implements UserMessageHandler<GetHistoryRequest> {

    private final UserActorApi userActorApi;

    @Override
    public void handle(HandlerContext<UserActorData> context, GetHistoryRequest request) {
        UserActorData actorData = context.getActorData();
        actorData.setReplyHistoryResultTo(request.getReplyTo());
        actorData.getPendingHistoryUsers().clear();

        if (actorData.getFollowers().isEmpty()) {
            request.safeReply(GetHistoryResponse.builder().build());
            return;
        }

        actorData.getFollowers().forEach(followedUserId -> {
            actorData.getPendingHistoryUsers().add(followedUserId);
            userActorApi.send(GetTweetsRequest.builder()
                    .replyTo(context.getSelf())
                    .userId(followedUserId)
                    .build());
        });
    }

    @Override
    public Class<GetHistoryRequest> messageClass() {
        return GetHistoryRequest.class;
    }
}
