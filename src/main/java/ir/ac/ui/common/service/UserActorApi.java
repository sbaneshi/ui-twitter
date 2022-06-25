package ir.ac.ui.common.service;

import akka.actor.typed.ActorRef;
import akka.japi.function.Function;
import ir.ac.ui.user.actor.api.UserMessage;

import java.util.concurrent.CompletableFuture;

public interface UserActorApi {

    void send(UserMessage message);

    <T> CompletableFuture<T> ask(Function<ActorRef<T>, UserMessage> messageCreator);
}
