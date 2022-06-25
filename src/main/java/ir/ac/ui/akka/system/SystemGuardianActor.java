package ir.ac.ui.akka.system;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import ir.ac.ui.akka.model.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemGuardianActor extends AbstractBehavior<Message> {

    public SystemGuardianActor(ActorContext<Message> context) {
        super(context);
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onAnyMessage(this::logReceivedMessage)
                .build();
    }

    private Behavior<Message> logReceivedMessage(Message x) {
        log.info("Received guardian message=[{}]", x);
        return this;
    }
}
