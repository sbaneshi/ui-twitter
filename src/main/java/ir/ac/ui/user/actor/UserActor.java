package ir.ac.ui.user.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import ir.ac.ui.akka.base.BaseActor;
import ir.ac.ui.user.actor.api.UserMessage;
import ir.ac.ui.user.actor.api.init.BecomeNormal;
import ir.ac.ui.user.actor.api.init.BecomeSignup;
import ir.ac.ui.user.actor.api.init.InitUserRequest;
import ir.ac.ui.user.actor.api.signup.SignupRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserActor extends BaseActor<UserActorData, UserMessage> {

    private UserActor(ActorContext<UserMessage> context, long userId) {
        super(context, new UserActorData(userId));

        log.info("Initializing User[{}]...", userId);
        context.getSelf().tell(InitUserRequest.INSTANCE);
    }

    public static Behavior<UserMessage> setup(long userId) {
        return Behaviors.setup(x -> new UserActor(x, userId));
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Class handlerClass() {
        return UserMessageHandler.class;
    }

    @Override
    public Receive<UserMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(InitUserRequest.class, this::handleRequest)
                .onMessage(BecomeSignup.class, x -> createSignupReceive())
                .onMessage(BecomeNormal.class, x -> createNormalReceive())
                .onAnyMessage(this::postponeProcessingMessage)
                .build();
    }

    private Receive<UserMessage> createSignupReceive() {
        return newReceiveBuilder()
                .onMessage(SignupRequest.class, this::handleRequest)
                .onMessage(BecomeNormal.class, x -> createNormalReceive())
                .onAnyMessage(this::handleInvalidRequest)
                .build();
    }

    private Receive<UserMessage> createNormalReceive() {
        return newReceiveBuilder()
                .onAnyMessage(this::handleRequest)
                .build();
    }
}
