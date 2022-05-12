package com.sip.api.domains.availableClass;

import com.sip.api.domains.TimeTrackable;
import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.user.User;
import com.sip.api.exceptions.BadRequestException;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availableClass")
public class AvailableClass extends TimeTrackable {

    /**
     * The activity that is dictated for the class
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_id", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Activity activity;

    /**
     * The timeslot that the class is available in
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "timeslot_id", nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Timeslot timeslot;

    /**
     * The attendees that had booked this class
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "available_class_user", joinColumns = @JoinColumn(name = "available_class_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_available_class_user")),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_available_class_user")))
    @Fetch(FetchMode.JOIN)
    private Set<User> attendees = new HashSet<>();

    /**
     * The attendees that tried to join the class but were not allowed due to unavailability.
     */
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> rejectedAttendees = new HashSet<>();

    public void addAttendee(User user) {
        if (this.attendees.size() + 1 >= this.activity.getAtendeesLimit()){
            this.rejectedAttendees.add(user);
            throw new BadRequestException(String.format("AvailableClass '%s' is full!", super.getId()));
        }
        this.attendees.add(user);
    }

    public void removeAttendee(User user) {
        this.attendees.remove(user);
    }

    public void addRejectedAttendee(User attendee){
        this.rejectedAttendees.add(attendee);
    }
}