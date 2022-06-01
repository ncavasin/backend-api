package com.sip.api.migration.migration;

import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.migration.StartUpMigration;
import com.sip.api.services.TimeslotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeslotMigration implements StartUpMigration {
    private final TimeslotService timeslotService;

    @Override
    public void run(ApplicationReadyEvent event) {
        createTimeslots();
    }

    private void createTimeslots() {
        for (int i = 7; i < 13; i++) {
            for (int dayAsNumber = 1; dayAsNumber < 6; dayAsNumber++) {
                LocalTime startTime = LocalTime.of(i, 15);
                LocalTime endTime = startTime.plusHours(1);
                DayOfWeek dayOfWeek = DayOfWeek.of(dayAsNumber);
                createTimeslot(startTime, endTime, dayOfWeek);
            }
        }
        for (int i = 16; i < 22; i++) {
            for (int dayAsNumber = 1; dayAsNumber < 6; dayAsNumber++) {
                LocalTime startTime = LocalTime.of(i, 15);
                LocalTime endTime = startTime.plusHours(1);
                DayOfWeek dayOfWeek = DayOfWeek.of(dayAsNumber);
                createTimeslot(startTime, endTime, dayOfWeek);
            }
        }
    }

    private void createTimeslot(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        try {
            timeslotService.createTimeslot(TimeslotCreationDto.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .dayOfWeek(dayOfWeek)
                    .build());
            log.info("Timeslot from {} to {} on {} created.", startTime, endTime, dayOfWeek);
        } catch (BadRequestException e) {
            log.info("Creation skipped. Timeslot from {} to {} on {} already exists.", startTime, endTime, dayOfWeek);
        }
    }


    public DayOfWeek getRandomDay() {
        return DayOfWeek.values()[(int) (Math.random() * 5)];
    }

    @Override
    public String getName() {
        return "Creation of Timeslots";
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
