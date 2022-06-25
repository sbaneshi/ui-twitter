package ir.ac.ui.akka.sharding;

import akka.cluster.sharding.typed.ShardingMessageExtractor;
import ir.ac.ui.akka.sharding.api.ShardingMessage;

public class DefaultShardMessageExtractor<T extends ShardingMessage> extends ShardingMessageExtractor<T, T> {

    private static final int NUMBER_OF_SHARDS = 10000;

    @Override
    public String entityId(T message) {
        return String.valueOf(message.getShardEntityId());
    }

    @Override
    public String shardId(String entityId) {
        return String.valueOf(Long.parseLong(entityId) % NUMBER_OF_SHARDS);
    }

    @Override
    public T unwrapMessage(T message) {
        return message;
    }
}
