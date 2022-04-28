package com.sip.api.domains.timeslot;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.appointment.Appointment;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timeslot")
public class Timeslot extends TimeTrackable {
    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @OneToMany(mappedBy = "timeslot", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<Appointment> appointments = new java.util.LinkedHashSet<>();
}
