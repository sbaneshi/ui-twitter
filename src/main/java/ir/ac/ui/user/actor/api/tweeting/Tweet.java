package ir.ac.ui.user.actor.api.tweeting;

import ir.ac.ui.akka.model.SerializableMessage;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Tweet implements SerializableMessage {

    long senderUserId;
    long date;
    String message;
}
