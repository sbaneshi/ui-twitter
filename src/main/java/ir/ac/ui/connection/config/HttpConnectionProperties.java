package ir.ac.ui.connection.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("ui.twitter.http")
public class HttpConnectionProperties {

    private String server = "0.0.0.0";
    private int port = 8888;
    private int concurrency = 1000;
}
