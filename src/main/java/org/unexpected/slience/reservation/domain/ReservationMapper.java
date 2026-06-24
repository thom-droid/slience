package org.unexpected.slience.reservation.domain;

import org.unexpected.slience.movie.domain.entity.MovieEntity;
import org.unexpected.slience.reservation.api.response.CreateReservationResponse;
import org.unexpected.slience.reservation.domain.entity.ReservationEntity;
import org.unexpected.slience.schedule.domain.entity.ScheduleEntity;
import org.unexpected.slience.screen.domain.SeatEntity;
import org.unexpected.slience.util.DateUtil;

import java.util.stream.Collectors;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static void updateEntity(Reservation r, ReservationEntity e) {
        e.setReservedAt(r.getReservedAt());
        e.setExpiresAt(r.getExpiresAt());
        e.setStatus(r.getStatus());
    }

    public static CreateReservationResponse toCreateReservationResponse(ReservationEntity entity) {
        MovieEntity movie = entity.getReservationScheduleSeats().get(0).getScheduleSeatEntity().getSchedule().getMovie();
        ScheduleEntity schedule = entity.getReservationScheduleSeats().get(0).getScheduleSeatEntity().getSchedule();
        String seatInfo = entity.getReservationScheduleSeats().stream()
                .map(rss -> {
                    SeatEntity st = rss.getScheduleSeatEntity().getSeat();
                    return st.getSeatRow() + st.getSeatNumber();
                }).collect(Collectors.joining(", "));

        return new CreateReservationResponse(
            movie.getId(),
                movie.getMovieNm(),
                movie.getMovieNmEn(),
                schedule.getScreen().getId(),
                schedule.getScreen().getName(),
                schedule.getId(),
                DateUtil.parseLocalDateTimeToString(schedule.getStartTime()),
                DateUtil.parseLocalDateTimeToString(schedule.getEndTime()),
                seatInfo,
                entity.getId(),
                DateUtil.parseLocalDateTimeToString(entity.getCreatedAt()),
                entity.getStatus(),
                DateUtil.parseLocalDateTimeToString(entity.getExpiresAt())
        );
    }

}
