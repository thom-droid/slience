package org.unexpected.slience.schedule.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.schedule.infra.ScheduleRepository;

@RequiredArgsConstructor
@Service
public class ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public int syncSchedules() {
        return scheduleRepository.syncSchedules();
    }
}
