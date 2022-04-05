package com.sip.api.domains.role;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.resource.Resource;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends TimeTrackable {
    @Column(nullable = false, unique = true)
    @NaturalId
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_resources",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_role_id")),
            inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_resource_id")))
    @Fetch(FetchMode.JOIN)
    Set<Resource> allowedResources = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.id);
    }

}