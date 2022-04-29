package com.sip.api.controllers;

import com.sip.api.domains.timeslot.TimeslotConverter;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.services.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/timeslot")
@RequiredArgsConstructor
public class TimeslotController {
    private final TimeslotService timeslotService;

    @GetMapping("/all")
    public List<TimeslotDto> findAll() {
        return TimeslotConverter.fromEntityToDto(timeslotService.findAll());
    }

    @GetMapping("/{timeslotId}")
    public TimeslotDto findById(@PathVariable("timeslotId") String timeslotId) {
        return TimeslotConverter.fromEntityToDto(timeslotService.findById(timeslotId));
    }

    @PostMapping
    public TimeslotDto createTimeslot(@Valid @RequestBody TimeslotCreationDto timeslotCreationDto){
        return TimeslotConverter.fromEntityToDto(timeslotService.createTimeslot(timeslotCreationDto));
    }

    @PutMapping("/{timeslotId}")
    public TimeslotDto updateTimeslot(@PathVariable("timeslotId") String timeslotId, @Valid @RequestBody TimeslotDto timeslotDto){
        return TimeslotConverter.fromEntityToDto(timeslotService.updateTimeslot(timeslotId, timeslotDto));
    }

    @DeleteMapping("/{timeslotId}")
    public void deleteTimeslot(@PathVariable("timeslotId") String timeslotId){
        timeslotService.deleteTimeslot(timeslotId);
    }
}
