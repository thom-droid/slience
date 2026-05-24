package org.unexpected.slience.kobis.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="directors_temp")
@Entity
public class DirectorTempEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long batchId;
    private String movieCd;
    private String name;
}
