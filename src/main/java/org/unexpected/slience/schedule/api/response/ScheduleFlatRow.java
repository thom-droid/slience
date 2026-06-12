package org.unexpected.slience.schedule.api.response;

import org.unexpected.slience.movie.domain.entity.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScheduleFlatRow(Long scheduleId,
                              Long movieId,
                              String movieCd,
                              String movieNm,
                              String movieNmEn,
                              LocalDate releaseDate,
                              String showTime,
                              Status status,
                              String typeNm,
                              String repNationNm,
                              String repGenreNm,
                              boolean adultYn,
                              Long directorId,
                              String directorNm,
                              Long screenId,
                              String name,
                              int seatsLeft,
                              int totalSeats,
                              boolean bookedOut,
                              LocalDateTime startDateTime,
                              LocalDateTime endDateTime,
                              Long seatId,
                              String seatRow,
                              String seatNumber,
                              boolean booked) { }
