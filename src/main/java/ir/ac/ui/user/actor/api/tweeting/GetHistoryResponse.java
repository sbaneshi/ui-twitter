package ir.ac.ui.user.actor.api.tweeting;

import ir.ac.ui.akka.model.SerializableMessage;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@Jacksonized
public class GetHistoryResponse implements SerializableMessage {

    @Builder.Default
    List<Tweet> tweets = new ArrayList<>();
}
