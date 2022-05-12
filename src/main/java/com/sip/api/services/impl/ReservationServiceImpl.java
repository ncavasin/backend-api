//package com.sip.api.services.impl;
//
//import com.sip.api.domains.availableClass.AvailableClass;
//import com.sip.api.domains.reservation.Reservation;
//import com.sip.api.dtos.reservation.ReservationCreationDto;
//import com.sip.api.exceptions.BadRequestException;
//import com.sip.api.repositories.ReservationRepository;
//import com.sip.api.services.AvailableClassService;
//import com.sip.api.services.ReservationService;
//import com.sip.api.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ReservationServiceImpl implements ReservationService {
//    private final ReservationRepository reservationRepository;
//    private final AvailableClassService availableClassService;
//    private final UserService userService;
//
//    @Override
//    public List<Reservation> findAll() {
//        return reservationRepository.findAll();
//    }
//
//    @Override
//    public Reservation findById(String reservationId) {
//        return reservationRepository.findById(reservationId).orElseThrow(() -> new BadRequestException("Reservation not found"));
//    }
//
//    @Override
//    public Reservation createReservation(ReservationCreationDto reservationCreationDto) {
//        AvailableClass availableClass = availableClassService.findById(reservationCreationDto.getAvailableClassId());
////        if  < availableClass.getActivity().getAtendeesLimit(){
////
////        }
//        Reservation reservation = Reservation.builder()
//                .availableClass(availableClass)
//                .attendees(reservationCreationDto.getAttendeesIds()
//                        .stream()
//                        .map(userService::findById)
//                        .collect(Collectors.toSet()))
//                .build();
//
//        return reservationRepository.save(reservation);
//    }
//
//    @Override
//    public void deleteReservation(String reservationId) {
//        if (!reservationRepository.existsById(reservationId))
//            throw new BadRequestException("Reservation not found");
//        reservationRepository.deleteById(reservationId);
//    }
//}
