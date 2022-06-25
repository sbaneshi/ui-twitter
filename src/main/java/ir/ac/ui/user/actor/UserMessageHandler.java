package ir.ac.ui.user.actor;

import ir.ac.ui.akka.base.MessageHandler;
import ir.ac.ui.user.actor.api.UserMessage;

public interface UserMessageHandler<M extends UserMessage> extends MessageHandler<UserActorData, M> {
}
