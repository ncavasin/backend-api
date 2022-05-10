package com.sip.api.repositories;

import com.sip.api.domains.reservation.Reservation;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Override
    @NonNull
    List<Reservation> findAll();

    boolean existsById(@NonNull String reservationId);
}
