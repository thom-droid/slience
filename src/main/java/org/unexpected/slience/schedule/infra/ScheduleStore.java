package org.unexpected.slience.schedule.infra;

import org.unexpected.slience.schedule.domain.Schedule;

public interface ScheduleStore {
    Schedule findById(Long id);
    Schedule save(Schedule schedule);
}
