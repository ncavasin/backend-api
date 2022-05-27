package com.sip.api.service;

import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.services.TimeslotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class TimeslotServiceImplTest {
    @Autowired
    private TimeslotService timeslotService;
    private final DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;
    private final LocalTime startTime = LocalTime.of(10, 0, 0);
    private final LocalTime endTime = startTime.plusHours(1);
    private Timeslot savedTimeslot;

    @Before
    @Transactional
    public void setup() {
        savedTimeslot = generateTimeslot(startTime, endTime, dayOfWeek);
    }

    @Test
    @Transactional
    public void createValidTimeslot() {
        Assert.assertEquals(timeslotService.findById(savedTimeslot.getId()), savedTimeslot);
    }

    @Test
    @Transactional
    public void createTimeslotWithSameValues_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateTimeslot(startTime, endTime, dayOfWeek));
    }

    @Test
    @Transactional
    public void createTimeslotWithEndDateBeforeStartDate_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateTimeslot(endTime, startTime, dayOfWeek));
    }

    @Test
    @Transactional
    public void createTimeslotOverlappingAnother_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class,
                () -> generateTimeslot(startTime.minusMinutes(15), endTime.minusMinutes(15), dayOfWeek));
    }

    @Test
    @Transactional
    public void updateTimeslotDay() {
        DayOfWeek newDayOfWeek = DayOfWeek.SATURDAY;

        TimeslotDto updateDto = TimeslotDto.builder()
                .id(savedTimeslot.getId())
                .startTime(savedTimeslot.getStartTime())
                .endTime(savedTimeslot.getEndTime())
                .dayOfWeek(newDayOfWeek)
                .build();

        Timeslot updated = timeslotService.updateTimeslot(updateDto.getId(), updateDto);

        Assert.assertNotEquals(timeslotService.findById(updated.getId()).getDayOfWeek(), dayOfWeek);
        Assert.assertEquals(timeslotService.findById(updated.getId()).getDayOfWeek(), newDayOfWeek);
    }

    @Test
    @Transactional
    public void updateInvalidTimeslotWithEndDateBeforeStartDate_shouldThrowBadRequest() {
        LocalTime newStartTime = LocalTime.of(16, 0, 0);

        TimeslotDto updateDto = TimeslotDto.builder()
                .id(savedTimeslot.getId())
                .startTime(newStartTime)
                .endTime(savedTimeslot.getEndTime())
                .dayOfWeek(savedTimeslot.getDayOfWeek())
                .build();

        Assert.assertThrows(BadRequestException.class, () -> timeslotService.updateTimeslot(savedTimeslot.getId(), updateDto));
    }

    @Test
    @Transactional
    public void updateTimeslotStartTime() {
        LocalTime newStartTime = LocalTime.of(22, 0, 0);

        TimeslotDto updateDto = TimeslotDto.builder()
                .id(savedTimeslot.getId())
                .startTime(newStartTime)
                .endTime(newStartTime.plusHours(1))
                .dayOfWeek(savedTimeslot.getDayOfWeek())
                .build();

        Timeslot updated = timeslotService.updateTimeslot(savedTimeslot.getId(), updateDto);

        Assert.assertNotEquals(timeslotService.findById(updated.getId()).getStartTime(), startTime);
        Assert.assertEquals(timeslotService.findById(updated.getId()).getStartTime(), newStartTime);
    }

    @Test
    @Transactional
    public void updateTimeslotEndTime() {
        LocalTime newEndTime = LocalTime.of(23, 0, 0);

        TimeslotDto updateDto = TimeslotDto.builder()
                .id(savedTimeslot.getId())
                .startTime(newEndTime.minusHours(1))
                .endTime(newEndTime)
                .dayOfWeek(savedTimeslot.getDayOfWeek())
                .build();

        Timeslot updated = timeslotService.updateTimeslot(updateDto.getId(), updateDto);

        Assert.assertNotEquals(timeslotService.findById(updated.getId()).getEndTime(), endTime);
        Assert.assertEquals(timeslotService.findById(updated.getId()).getEndTime(), newEndTime);
    }

    @Test
    @Transactional
    public void deleteTimeslot() {
        timeslotService.deleteTimeslot(savedTimeslot.getId());
        Assert.assertThrows(NotFoundException.class, () -> timeslotService.findById(savedTimeslot.getId()));
    }

    private Timeslot generateTimeslot(LocalTime start, LocalTime end, DayOfWeek dayOfWeek) {
        return timeslotService
                .createTimeslot(TimeslotCreationDto.builder()
                        .startTime(start)
                        .endTime(end)
                        .dayOfWeek(dayOfWeek)
                        .build());
    }
}
