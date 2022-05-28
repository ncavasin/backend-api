package com.sip.api.service;

import com.sip.api.domains.reservation.Reservation;
import com.sip.api.services.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class ReservationServiceImplTest {
    @Autowired
    private ReservationService reservationService;

    private Reservation savedReservation;


    @Before
    @Transactional
    public void setUp() {
        savedReservation = generateReservation();
    }

    @Test
    @Transactional
    public void createReservation() {

    }

    @Test
    @Transactional
    public void addUserToReservation() {

    }

    @Test
    @Transactional
    public void removeUserFromReservationUsingAvailableClassId() {

    }

    @Test
    @Transactional
    public void countAttendeeAmountByAvailableClassId() {

    }

    @Test
    @Transactional
    public void deleteReservation() {

    }

    private Reservation generateReservation() {
        return reservationService.addUserToReservation(null);
    }


}
