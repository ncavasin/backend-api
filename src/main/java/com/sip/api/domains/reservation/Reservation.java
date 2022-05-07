package com.sip.api.domains.reservation;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.availableClass.AvailableClass;
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
@Table(name = "reservation")
public class Reservation extends TimeTrackable {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "availableClass_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_availableClass_reservation_id"))
    @Fetch(FetchMode.JOIN)
    private Set<AvailableClass> availableClasses = new java.util.LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendee_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_reservation_atendee_id"))
    @Fetch(FetchMode.JOIN)
    private Set<User> attendees;
}