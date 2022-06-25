package ir.ac.ui.user.actor.handler;

import ir.ac.ui.akka.base.HandlerContext;
import ir.ac.ui.user.actor.UserActorData;
import ir.ac.ui.user.actor.UserMessageHandler;
import ir.ac.ui.user.actor.api.internal.GetTweetsRequest;
import ir.ac.ui.user.actor.api.internal.GetTweetsResult;
import ir.ac.ui.user.actor.api.tweeting.Tweet;
import ir.ac.ui.user.data.tweet.TweetEntity;
import ir.ac.ui.user.data.tweet.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetTweetsRequestHandler implements UserMessageHandler<GetTweetsRequest> {

    private final TweetRepository tweetRepository;

    @Override
    public void handle(HandlerContext<UserActorData> context, GetTweetsRequest message) {
        message.safeReply(GetTweetsResult.builder()
                .fromUserId(context.getActorData().getUserId())
                .tweets(getSelfTweets(context))
                .build());
    }

    private List<Tweet> getSelfTweets(HandlerContext<UserActorData> context) {
        return tweetRepository.findAllBySenderId(context.getActorData().getUserId()).stream()
                .map(x -> createTweet(context, x))
                .collect(Collectors.toList());
    }

    private Tweet createTweet(HandlerContext<UserActorData> context, TweetEntity x) {
        return Tweet.builder()
                .senderUserId(context.getActorData().getUserId())
                .date(x.getId())
                .message(x.getMessage())
                .build();
    }

    @Override
    public Class<GetTweetsRequest> messageClass() {
        return GetTweetsRequest.class;
    }
}
