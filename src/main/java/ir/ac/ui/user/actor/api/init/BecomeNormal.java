package ir.ac.ui.user.actor.api.init;

import ir.ac.ui.user.actor.api.InternalUserMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BecomeNormal implements InternalUserMessage {

    public static final BecomeNormal INSTANCE = new BecomeNormal();
}
