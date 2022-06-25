package ir.ac.ui.user.config;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.Entity;
import akka.cluster.sharding.typed.javadsl.EntityTypeKey;
import ir.ac.ui.akka.model.Message;
import ir.ac.ui.akka.sharding.DefaultShardMessageExtractor;
import ir.ac.ui.common.service.UserActorApi;
import ir.ac.ui.user.actor.UserActor;
import ir.ac.ui.user.actor.api.UserMessage;
import ir.ac.ui.user.service.UserActorApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserShardingConfig {

    private static final String USER_SHARDING_NAME = "user";

    private final ActorSystem<Message> actorSystem;

    @Bean
    public UserActorApi userActorApi() {
        log.info("Setting up user cluster sharding...");
        ClusterSharding sharding = ClusterSharding.get(actorSystem);
        EntityTypeKey<UserMessage> key = EntityTypeKey.create(UserMessage.class, USER_SHARDING_NAME);
        ActorRef<UserMessage> shardRegion = sharding.init(
                Entity.of(key, x -> UserActor.setup(Long.parseLong(x.getEntityId())))
                        .withMessageExtractor(new DefaultShardMessageExtractor())
                        .withRole("user")
        );

        return new UserActorApiService(actorSystem, shardRegion);
    }

}
