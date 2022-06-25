package ir.ac.ui.akka.base;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import ir.ac.ui.akka.model.ErrorResponse;
import ir.ac.ui.akka.model.Message;
import ir.ac.ui.twitter.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Map;

@Slf4j
public abstract class BaseActor<S extends ActorData, M extends Message> extends AbstractBehavior<M> {

    protected final HandlerContext<S> handlerContext;
    protected final MessageHandlerRegistry handlerRegistry;

    public BaseActor(ActorContext<M> context, S actorData) {
        super(context);
        handlerRegistry = initHandlerRegistry();
        handlerContext = HandlerContext.<S>builder()
                .self(getContext().getSelf())
                .actorData(actorData)
                .build();
    }

    @SuppressWarnings("unchecked")
    private MessageHandlerRegistry initHandlerRegistry() {
        Map<String, MessageHandler<S, M>> handlers = SpringUtils.getBeans(handlerClass());
        return new MessageHandlerRegistry(handlers.values());
    }

    @SuppressWarnings("rawtypes")
    protected abstract Class handlerClass();

    protected Behavior<M> handleRequest(M request) {
        MessageHandler<S, M> handler = handlerRegistry.resolveHandler(request);
        if (handler == null) {
            log.warn("No RequestHandler found for request=[{}], ", request.getClass().getSimpleName());
            request.safeReply(ErrorResponse.validation("HANDLER_NOT_FOUND"));
            return Behaviors.same();
        }

        try {
            final long beforeStart = System.currentTimeMillis();
            handler.handle(handlerContext, request);
            log.info("Duration of processing[{}] was [{}]ms",
                    request.getClass().getSimpleName(), System.currentTimeMillis() - beforeStart);
        } catch (Exception e) {
            log.error("Exception[{}] happened during handling Request=[{}]", e, request, e);
            if (e instanceof IllegalArgumentException)
                request.safeReply(ErrorResponse.validation(e.getMessage()));
            else
                request.safeReply(ErrorResponse.unexpected(e.getMessage()));
        }
        return Behaviors.same();
    }

    protected Behavior<M> handleInvalidRequest(M request) {
        log.warn("Invalid request received; request-class=[{}]", request.getClass().getSimpleName());
        request.safeReply(ErrorResponse.validation("INVALID_REQUEST"));
        return Behaviors.same();
    }

    protected Behavior<M> postponeProcessingMessage(M request) {
        getContext().scheduleOnce(Duration.ofMillis(100), getContext().getSelf(), request);
        return Behaviors.same();
    }
}
