package net.kravuar.giveaways.application.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app.websocket")
@Data
public class WebSocketProps {
    /*
     * List of allowed origins.
     * */
    private List<String> allowedOrigins;
}
