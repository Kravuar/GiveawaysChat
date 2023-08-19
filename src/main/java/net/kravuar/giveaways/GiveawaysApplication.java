package net.kravuar.giveaways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "net.kravuar.giveaways.application.props")
public class GiveawaysApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiveawaysApplication.class, args);
    }
}
