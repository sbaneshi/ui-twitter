package ir.ac.ui.akka.system;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import ir.ac.ui.akka.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AkkaSystemConfig {

    private static final String ACTOR_SYSTEM_NAME = "ui-twitter";

    @Bean
    public ActorSystem<Message> actorSystem(ApplicationContext context) {
        List<String> activeProfiles = Arrays.asList(context.getEnvironment().getActiveProfiles());
        log.info("Setting up ActorSystem[{}] with Roles{}...", ACTOR_SYSTEM_NAME, activeProfiles);

        return ActorSystem.create(
                Behaviors.setup(SystemGuardianActor::new), ACTOR_SYSTEM_NAME,
                ConfigFactory.defaultApplication()
                        .withValue("akka.cluster.roles", ConfigValueFactory.fromIterable(activeProfiles))
                        .resolve());
    }
}
