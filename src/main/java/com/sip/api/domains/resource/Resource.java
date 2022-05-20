package com.sip.api.domains.resource;


import com.sip.api.domains.TimeTrackable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource")
public class Resource extends TimeTrackable {
    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false, unique = true)
    private String name;

    // TODO: ADD HTTP METHODS
    // IT WORKS BUT I NEED TO IMPLEMENT THE CREATION OF THE RESOURCE ASSOCIATED TO A ROLE

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resource resource = (Resource) o;
        return id != null && Objects.equals(id, resource.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
