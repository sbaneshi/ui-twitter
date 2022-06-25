package ir.ac.ui.user.actor.api.internal;

import ir.ac.ui.user.actor.api.UserMessage;
import ir.ac.ui.user.actor.api.tweeting.Tweet;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@Jacksonized
public class GetTweetsResult implements UserMessage {

    long fromUserId;

    @Builder.Default
    List<Tweet> tweets = new ArrayList<>();

    @Override
    public long getShardEntityId() {
        return 0; // Because of jackson
    }
}
