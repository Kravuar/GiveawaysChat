package net.kravuar.fluxsocket.application.props;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("app.websocket")
@AllArgsConstructor
public class WebSocketProps {
    public List<String> allowedOrigins;
}
