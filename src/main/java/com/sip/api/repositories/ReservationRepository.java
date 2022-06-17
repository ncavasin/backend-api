package com.sip.api.repositories;

import com.sip.api.domains.plan.Plan;
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

    @Query(nativeQuery = true,
            value = "SELECT r.* " +
                    "FROM user_data u " +
                    "JOIN reservation_user_data rud on u.id = rud.user_id " +
                    "JOIN reservation r on rud.reservation_id = r.id " +
                    "WHERE u.id = :userId")
    List<Reservation> findAllByAttendeeId(String userId);

    @Query(nativeQuery = true,
            value = "SELECT count(*) " +
                    "FROM reservation r " +
                    "JOIN reservation_user_data rud ON r.id = rud.reservation_id " +
                    "JOIN available_class ac ON r.available_class_id = ac.id " +
                    "WHERE ac.id = :availableClassId")
    Integer countAttendeeAmountByAvailableClassId(String availableClassId);

    @Query(value = "SELECT r FROM Reservation r WHERE r.availableClass.id = ?1")
    Optional<Reservation> findByAvailableClass_Id(@NonNull String availableClassId);

    @Query(nativeQuery = true,
    value = "SELECT COUNT(*) FROM reservation r " +
            "JOIN reservation_user_data rud ON rud.reservation_id = r.id " +
            "JOIN user_data ud on rud.user_id = ud.id " +
            "WHERE ud.id = ?1")
    Integer countReservationsByUserId(String userId);

    @Query("SELECT (COUNT(r) > 0) FROM Reservation r WHERE r.id = ?1")
    boolean existsById(@NonNull String reservationId);
}
