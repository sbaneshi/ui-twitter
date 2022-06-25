package ir.ac.ui.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("user")
@ComponentScan("ir.ac.ui.user.actor")
public class UserActorConfig {
}
