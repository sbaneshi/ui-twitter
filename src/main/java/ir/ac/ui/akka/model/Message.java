package ir.ac.ui.akka.model;

public interface Message extends SerializableMessage {

    default <R> void safeReply(R reply) {
    }
}
