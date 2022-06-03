package com.sip.api.domains.plan;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.subscription.Subscription;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan")
public class Plan extends TimeTrackable {
    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer activitiesLimit;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<Subscription> subscriptions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Plan plan = (Plan) o;
        return id != null && Objects.equals(id, plan.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
