package org.unexpected.slience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SlienceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlienceApplication.class, args);
    }

}
