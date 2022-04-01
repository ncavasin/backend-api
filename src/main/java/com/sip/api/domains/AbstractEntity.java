package com.sip.api.domains;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "uuid2")
    protected String id;

}
