package com.sip.api.domains;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class TimeTrackable extends AbstractEntity {

    @CreationTimestamp
    protected Timestamp creationTimestamp;

    @UpdateTimestamp
    protected Timestamp updateTimestamp;
}
