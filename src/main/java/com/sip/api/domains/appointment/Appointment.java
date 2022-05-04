package com.sip.api.domains.appointment;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment extends TimeTrackable {
    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_activity_id"))
    @Fetch(FetchMode.JOIN)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "timeslot_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_timeslot_id"))
    @Fetch(FetchMode.JOIN)
    private Timeslot timeslot;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appointment")
    @JoinColumn(name = "appointment_details_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_appointment_details_id"))
    @Fetch(FetchMode.JOIN)
    private AppointmentDetail appointmentDetail;
}