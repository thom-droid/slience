package org.unexpected.slience.schedule.domain;

public class ScheduleEntityMapper {

    private ScheduleEntityMapper() { }

    public static Schedule toSchedule(ScheduleEntity e) {
        Schedule schedule = new Schedule();
        schedule.setId(e.getId());
        schedule.setStartTime(e.getStartTime());
        schedule.setEndTime(e.getEndTime());
        return schedule;
    }

    public static ScheduleEntity toEntity(Schedule schedule) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(schedule.getId());
        scheduleEntity.setStartTime(schedule.getStartTime());
        scheduleEntity.setEndTime(schedule.getEndTime());
        return scheduleEntity;
    }

}
