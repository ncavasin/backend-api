package com.sip.api.domains.timeslot;

import com.google.common.base.Objects;
import com.sip.api.domains.TimeTrackable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalTime;

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

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return Objects.equal(getId(), timeslot.getId()) && Objects.equal(getStartTime(), timeslot.getStartTime()) && Objects.equal(getEndTime(), timeslot.getEndTime()) && getDayOfWeek() == timeslot.getDayOfWeek();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getStartTime(), getEndTime(), getDayOfWeek());
    }
}
