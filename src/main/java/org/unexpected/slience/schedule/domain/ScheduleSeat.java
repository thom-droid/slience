package org.unexpected.slience.schedule.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleSeat {
    private long id;
    private long scheduleId;
    private long seatId;
}
