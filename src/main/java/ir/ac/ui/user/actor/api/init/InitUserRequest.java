package ir.ac.ui.user.actor.api.init;

import ir.ac.ui.user.actor.api.InternalUserMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InitUserRequest implements InternalUserMessage {

    public static final InitUserRequest INSTANCE = new InitUserRequest();
}
