package ir.ac.ui.user.actor;

import akka.actor.typed.ActorRef;
import ir.ac.ui.akka.base.ActorData;
import ir.ac.ui.user.actor.api.tweeting.GetHistoryResponse;
import ir.ac.ui.user.actor.api.tweeting.Tweet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserActorData implements ActorData {

    private final long userId;
    private final List<Long> followers = new ArrayList<>();

    private final List<Long> pendingHistoryUsers = new ArrayList<>();
    private final List<Tweet> receivedTweets = new ArrayList<>();
    private ActorRef<GetHistoryResponse> replyHistoryResultTo;
}
