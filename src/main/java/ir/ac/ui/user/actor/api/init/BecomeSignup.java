package ir.ac.ui.user.actor.api.init;

import ir.ac.ui.user.actor.api.InternalUserMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BecomeSignup implements InternalUserMessage {

    public static final BecomeSignup INSTANCE = new BecomeSignup();
}
