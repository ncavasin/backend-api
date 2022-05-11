package com.sip.api.domains.availableClass;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.reservation.Reservation;
import com.sip.api.domains.timeslot.Timeslot;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availableClass")
public class AvailableClass extends TimeTrackable {
    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_id", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "timeslot_id", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Timeslot timeslot;
}