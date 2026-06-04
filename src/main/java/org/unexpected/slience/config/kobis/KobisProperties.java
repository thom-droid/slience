package org.unexpected.slience.config.kobis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kobis")
public record KobisProperties(Api api) {

    public record Api(String scheme, String host, Path path, String key) {
    }

    public record Path (String list, String detail) {
    }
}
