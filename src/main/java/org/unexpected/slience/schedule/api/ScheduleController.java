package org.unexpected.slience.schedule.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unexpected.slience.schedule.api.response.ScheduleDetailResponse;
import org.unexpected.slience.schedule.application.ScheduleQueryService;

@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleQueryService scheduleQueryService;

    @GetMapping("/{id}")
    public ScheduleDetailResponse getScheduleDetail(@PathVariable(value = "id") Long scheduleId) {
        return scheduleQueryService.getScheduleDetail(scheduleId);
    }

}
