package ir.ac.ui.user.actor.api;

import ir.ac.ui.akka.model.BaseMessage;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class UserRequest extends BaseMessage implements UserMessage {

    long userId;

    @Override
    public long getShardEntityId() {
        return userId;
    }
}
