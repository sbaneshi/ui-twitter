package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.internal.GetTweetsResult;
import ir.ac.ui.user.actor.api.tweeting.GetHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetTweetsResultHandler implements UserMessageHandler<GetTweetsResult> {

    @Override
    public void handle(HandlerContext<UserActorData> context, GetTweetsResult message) {
        UserActorData actorData = context.getActorData();
        actorData.getPendingHistoryUsers().remove(message.getFromUserId());
        actorData.getReceivedTweets().addAll(message.getTweets());

        if (actorData.getPendingHistoryUsers().isEmpty()) {
            actorData.getReplyHistoryResultTo().tell(GetHistoryResponse.builder()
                    .tweets(actorData.getReceivedTweets())
                    .build());
        }
    }

    @Override
    public Class<GetTweetsResult> messageClass() {
        return GetTweetsResult.class;
    }
}
