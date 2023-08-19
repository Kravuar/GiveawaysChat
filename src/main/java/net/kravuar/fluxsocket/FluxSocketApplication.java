package net.kravuar.fluxsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "net.kravuar.fluxsocket.application.props")
public class FluxSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxSocketApplication.class, args);
    }
}
