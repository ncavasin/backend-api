package com.sip.api.services.impl;

import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.timeslot.TimeslotConverter;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.TimeslotRepository;
import com.sip.api.services.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {
    private final TimeslotRepository timeslotRepository;

    @Override
    public List<Timeslot> findAll() {
        return timeslotRepository.findAll();
    }

    @Override
    public List<Timeslot> findAllAvailableAndOrdered() {
        return timeslotRepository.findAllAvailableAndOrderedByStartTimeAndDayOfWeek();
    }

    @Override
    public Timeslot findById(String timeslotId) {
        return timeslotRepository.findById(timeslotId).orElseThrow(() -> new NotFoundException("Timeslot not found"));
    }

    @Override
    public Timeslot createTimeslot(TimeslotCreationDto timeslotCreationDto) {
        checkExistenceOverlappingAndPrecedingTimes(timeslotCreationDto.getStartTime(), timeslotCreationDto.getEndTime(), timeslotCreationDto.getDayOfWeek());
        return timeslotRepository.save(TimeslotConverter.fromDtoToEntity(timeslotCreationDto));
    }

    @Override
    public Timeslot updateTimeslot(String timeslotId, TimeslotDto timeslotDto) {
        if (!timeslotRepository.existsById(timeslotId))
            throw new NotFoundException("Timeslot not found");

        checkExistenceOverlappingAndPrecedingTimes(timeslotDto.getStartTime(), timeslotDto.getEndTime(), timeslotDto.getDayOfWeek());

        Timeslot timeslot = findById(timeslotId);
        timeslot.setStartTime(timeslotDto.getStartTime());
        timeslot.setEndTime(timeslotDto.getEndTime());
        timeslot.setDayOfWeek(timeslotDto.getDayOfWeek());
        return timeslotRepository.save(timeslot);
    }

    @Override
    public void deleteTimeslot(String timeslotId) {
        timeslotRepository.deleteById(timeslotId);
    }

    private void checkExistenceOverlappingAndPrecedingTimes(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        checkExistenceByStartTimeEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek);
        checkOverlapping(startTime, endTime, dayOfWeek);
        checkEndTimeIsBiggerThanStartTime(startTime, endTime);
    }

    private void checkExistenceByStartTimeEndTimeAndDayOfWeek(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        if (timeslotRepository.existsByStartTimeAndEndTimeAndDayOfWeek(startTime, endTime, dayOfWeek))
            throw new BadRequestException(String.format("Timeslot from %s to %s already exists", startTime, endTime));
    }

    private void checkOverlapping(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        if (isOverlappingWithExistingTimeslot(startTime, endTime, dayOfWeek))
            throw new BadRequestException("Timeslot overlaps with existing timeslot");
    }

    private boolean isOverlappingWithExistingTimeslot(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        List<Timeslot> timeslots = timeslotRepository.findAll();
        return timeslots.stream()
                .anyMatch(timeslot -> dayOfWeek.equals(timeslot.getDayOfWeek())
                        &&
                        ((startTime.isBefore(timeslot.getStartTime()) && endTime.isAfter(timeslot.getStartTime()) && endTime.isBefore(timeslot.getEndTime()))
                                ||
                                (startTime.isAfter(timeslot.getStartTime()) && startTime.isBefore(timeslot.getEndTime())) && endTime.isAfter(timeslot.getEndTime())));
    }

    private void checkEndTimeIsBiggerThanStartTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime))
            throw new BadRequestException("Start time cannot be after end time");
    }
}
