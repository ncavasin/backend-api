package com.sip.api.domains.reservation;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.user.User;
import com.sip.api.exceptions.BadRequestException;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation extends TimeTrackable {
    /**
     * A reservation only has one AvailableClass
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private AvailableClass availableClass;

    /**
     * A reservation for an available class can have many users and a user can have a reservation
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_user_data",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Fetch(FetchMode.JOIN)
    private Set<User> attendees;

    public void addAttendee(User user) {
        if (this.getAttendees().size() >= this.getAvailableClass().getActivity().getAttendeesLimit())
            throw new BadRequestException(String.format("No more slots available for AvailableClass %s.", this.getAvailableClass().getId()));
        attendees.add(user);
    }

    public void removeAttendee(User user) {
        attendees.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation that = (Reservation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}