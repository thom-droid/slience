package org.unexpected.slience.config.kobis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "kobis")
@Component
@Getter
@Setter
public class KobisProperties {

    private Api api;

    @Getter
    @Setter
    public static class Api {
        private String url;
        private String key;
    }
}
