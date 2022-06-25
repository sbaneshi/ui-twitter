package ir.ac.ui.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("user")
@EntityScan("ir.ac.ui.user.data")
@EnableJpaRepositories(basePackages = "ir.ac.ui.user.data")
public class UserDataConfig {
}
