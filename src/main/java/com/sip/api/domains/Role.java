package com.sip.api.domains;

import com.sip.api.domains.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class Role extends TimeTrackable {
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    @Column(nullable = false, unique = true)
    private String roleName;

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