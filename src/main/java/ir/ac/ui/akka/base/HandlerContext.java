package ir.ac.ui.akka.base;

import akka.actor.typed.ActorRef;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HandlerContext<S extends ActorData> {

    ActorRef<?> self;
    S actorData;

    @SuppressWarnings("unchecked")
    public <M> ActorRef<M> getSelf() {
        return (ActorRef<M>) self;
    }
}
