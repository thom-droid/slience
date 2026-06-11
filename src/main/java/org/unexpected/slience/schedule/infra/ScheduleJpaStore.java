package org.unexpected.slience.schedule.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.schedule.application.exception.NoScheduleFoundException;
import org.unexpected.slience.schedule.domain.Schedule;
import org.unexpected.slience.schedule.domain.ScheduleEntity;
import org.unexpected.slience.schedule.domain.ScheduleEntityMapper;

@RequiredArgsConstructor
@Repository
public class ScheduleJpaStore implements ScheduleStore {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .map(ScheduleEntityMapper::toSchedule)
                .orElseThrow(() -> new NoScheduleFoundException(id));
    }

    @Override
    public Schedule save(Schedule schedule) {
        ScheduleEntity save = scheduleRepository.save(ScheduleEntityMapper.toEntity(schedule));
        return ScheduleEntityMapper.toSchedule(save);
    }
}
