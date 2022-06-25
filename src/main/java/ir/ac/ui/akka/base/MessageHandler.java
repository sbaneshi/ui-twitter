package ir.ac.ui.akka.base;

import ir.ac.ui.akka.model.Message;

public interface MessageHandler<S extends ActorData, M extends Message> {

    void handle(HandlerContext<S> context, M message);

    Class<M> messageClass();
}
