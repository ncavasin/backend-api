package com.sip.api.domains.plan;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.subscription.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
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
}
