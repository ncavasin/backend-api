package com.sip.api.domains.activity;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.user.User;
import lombok.*;
import org.checkerframework.checker.index.qual.NonNegative;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity extends TimeTrackable {
    @Column(nullable = false)
    private String name;

    @NonNegative
    @Column(nullable = false)
    private Double basePrice;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User professor;

    @NonNegative
    private int attendeesLimit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Activity activity = (Activity) o;
        return id != null && Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
