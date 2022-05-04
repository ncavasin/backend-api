package com.sip.api.domains.activity;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.appointment.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.index.qual.NonNegative;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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
