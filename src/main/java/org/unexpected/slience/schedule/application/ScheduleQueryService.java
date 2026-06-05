package org.unexpected.slience.schedule.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.schedule.domain.ScheduleEntity;
import org.unexpected.slience.schedule.infra.ScheduleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;

}
