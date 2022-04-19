package com.sip.api.domains.appointment;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment_detail")
public class AppointmentDetail extends TimeTrackable {
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "appointment_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_appointment_detail_appointment_id"))
    @Fetch(FetchMode.JOIN)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_appointment_detail_professor_id"))
    @Fetch(FetchMode.JOIN)
    private User professor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "appointment_detail_user",
            joinColumns = @JoinColumn(name = "appointment_detail_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_appointment_detail_user_appointment_details_id")),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_appointment_detail_user_user_id")))
    @Fetch(FetchMode.JOIN)
    private Set<User> attendees;
}