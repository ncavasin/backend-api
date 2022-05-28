package com.sip.api.service;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.reservation.Reservation;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.reservation.ReservationCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.services.ActivityService;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.ReservationService;
import com.sip.api.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class ReservationServiceImplTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private AvailableClassService availableClassService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    private AvailableClass savedAvailableClass;
    private User attendee;
    private User attendee2;
    private Reservation savedReservation;


    @Before
    @Transactional
    public void setUp() {
        List<Activity> activities = activityService.findAll();
        Activity savedActivity = activities.get(activities.size() - 1);
        savedAvailableClass = availableClassService.findByActivityId(savedActivity.getId()).get(0);

        attendee = userService.findByEmail(UserEmailDto.builder().email("analyst@mail.com").build());
        attendee2 = userService.findByEmail(UserEmailDto.builder().email("admin@admin.com").build());

        savedReservation = reservationService.addUserToReservation(ReservationCreationDto.builder()
                .attendeeId(attendee.getId())
                .availableClassId(savedAvailableClass.getId())
                .build());
    }

    @Test
    @Transactional
    public void addUserToReservation() {
        Assert.assertEquals(reservationService.findById(savedReservation.getId()), savedReservation);
    }

    @Test
    @Transactional
    public void countAttendeeAmountByAvailableClassId() {
        final Reservation found = reservationService.findById(savedReservation.getId());
        final Integer attendeeAmount = reservationService.countAttendeeAmountByAvailableClassId(found.getAvailableClass().getId());
        Assert.assertEquals(1, (int) attendeeAmount);
    }

    @Test
    @Transactional
    public void addUserToReservation_shouldNotAddWhenAvailableClassIsFull() {
        Assert.assertThrows(BadRequestException.class, () -> reservationService.addUserToReservation(ReservationCreationDto.builder()
                .attendeeId(attendee2.getId())
                .availableClassId(savedAvailableClass.getId())
                .build()));
    }

    @Test
    @Transactional
    public void removeUserFromReservationUsingAvailableClassId() {
        final Reservation found = reservationService.findById(savedReservation.getId());
        Assert.assertTrue(found.getAttendees().contains(attendee));

        final Reservation updated = reservationService.removeUserFromReservationUsingAvailableClassId(attendee.getId(), found.getAvailableClass().getId());
        Assert.assertFalse(updated.getAttendees().contains(attendee));
    }

    @Test
    @Transactional
    public void deleteReservation() {
        reservationService.deleteReservation(savedReservation.getId());
        Assert.assertThrows(NotFoundException.class, () -> reservationService.findById(savedReservation.getId()));
    }
}
