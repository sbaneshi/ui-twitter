package ir.ac.ui.user.actor.api;

public interface InternalUserMessage extends UserMessage {

    @Override
    default <R> void safeReply(R reply) {
        throw new UnsupportedOperationException();
    }

    @Override
    default long getShardEntityId() {
        throw new UnsupportedOperationException();
    }
}
