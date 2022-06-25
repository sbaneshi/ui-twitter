package ir.ac.ui.akka.base;

import ir.ac.ui.akka.model.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

@Value
public class MessageHandlerRegistry {

    @Getter(AccessLevel.PRIVATE)
    Map<Class<?>, MessageHandler<?, ?>> registry = new HashMap<>();

    public MessageHandlerRegistry(Iterable<? extends MessageHandler<?, ?>> handlers) {
        handlers.forEach(x -> registry.put(x.messageClass(), x));
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <S extends ActorData, M extends Message> MessageHandler<S, M> resolveHandler(M request) {
        return (MessageHandler<S, M>) registry.get(request.getClass());
    }
}
