package ir.ac.ui.akka.model;

import akka.actor.typed.ActorRef;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;

@Value
@NonFinal
@SuperBuilder
public abstract class BaseMessage implements Message {

    @Nullable
    ActorRef<?> replyTo;

    @Nullable
    @SuppressWarnings("unchecked")
    public <M> ActorRef<M> getReplyTo() {
        return (ActorRef<M>) replyTo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> void safeReply(R reply) {
        if (replyTo != null) ((ActorRef<R>) replyTo).tell(reply);
    }
}
