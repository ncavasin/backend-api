package com.sip.api.repositories;

import com.sip.api.domains.reservation.Reservation;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Override
    @NonNull
    List<Reservation> findAll();

    @Query(value = "select r from Reservation r where r.availableClass.id = ?1")
    Optional<Reservation> findByAvailableClass_Id(@NonNull String availableClassId);

    @Query("select (count(r) > 0) from Reservation r where r.id = ?1")
    boolean existsById(@NonNull String reservationId);
}
