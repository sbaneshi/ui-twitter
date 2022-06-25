package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.tweeting.SendTweetRequest;
import ir.ac.ui.user.actor.api.tweeting.SendTweetResponse;
import ir.ac.ui.user.data.tweet.TweetEntity;
import ir.ac.ui.user.data.tweet.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendTweetRequestHandler implements UserMessageHandler<SendTweetRequest> {

    private final TweetRepository tweetRepository;

    @Override
    public void handle(HandlerContext<UserActorData> context, SendTweetRequest request) {
        TweetEntity savedEntity = tweetRepository.save(createEntity(context.getActorData(), request));
        request.safeReply(SendTweetResponse.builder()
                .tweetId(savedEntity.getId())
                .build());
    }

    private TweetEntity createEntity(UserActorData data, SendTweetRequest request) {
        TweetEntity entity = new TweetEntity();
        entity.setSenderId(data.getUserId());
        entity.setSentDate(System.currentTimeMillis());
        entity.setMessage(request.getMessage());
        return entity;
    }

    @Override
    public Class<SendTweetRequest> messageClass() {
        return SendTweetRequest.class;
    }
}
