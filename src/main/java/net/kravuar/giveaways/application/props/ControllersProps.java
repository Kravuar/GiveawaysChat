package net.kravuar.giveaways.application.props;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.controllers")
@AllArgsConstructor
public class ControllersProps {
    public Integer pageSize;
}
