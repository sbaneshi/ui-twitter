package ir.ac.ui.connection.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("connection")
@ComponentScan("ir.ac.ui.connection.http")
@EnableConfigurationProperties(HttpConnectionProperties.class)
public class HttpConnectionConfig {
}
