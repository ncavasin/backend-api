package com.sip.api.domains.activity;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.appointment.Appointment;
import lombok.*;
import org.checkerframework.checker.index.qual.NonNegative;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

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

    @OneToMany(mappedBy = "activity")
    @Fetch(FetchMode.JOIN)
    private Set<Appointment> appointments = new java.util.LinkedHashSet<>();
}
