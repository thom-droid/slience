package org.unexpected.slience.config;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Configuration
@EnableJpaAuditing
@MappedSuperclass
public abstract class Auditable {

    private static final Logger log = LoggerFactory.getLogger(Auditable.class);

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
