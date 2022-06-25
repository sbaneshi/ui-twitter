package ir.ac.ui.user.actor.api.signup;

import ir.ac.ui.akka.model.SerializableMessage;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class SignupResponse implements SerializableMessage {

    long userId;
}
