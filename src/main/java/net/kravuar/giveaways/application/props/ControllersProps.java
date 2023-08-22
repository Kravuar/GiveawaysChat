package net.kravuar.giveaways.application.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.controllers")
@Data
public class ControllersProps {
    /*
     * Page size for pageable queries.
     * */
    private Integer pageSize = 10;
}
