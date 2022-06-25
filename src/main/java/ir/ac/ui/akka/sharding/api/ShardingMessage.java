package ir.ac.ui.akka.sharding.api;

import ir.ac.ui.akka.model.Message;
import ir.ac.ui.akka.model.SerializableMessage;

public interface ShardingMessage extends Message, SerializableMessage {

    long getShardEntityId();
}
