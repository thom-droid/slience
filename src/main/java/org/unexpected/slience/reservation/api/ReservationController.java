package org.unexpected.slience.reservation.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.unexpected.slience.reservation.api.request.CreateReservationRequest;
import org.unexpected.slience.reservation.api.response.CreateReservationResponse;
import org.unexpected.slience.reservation.application.ReservationCommandService;
import org.unexpected.slience.reservation.domain.ReservationMapper;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
@RestController
public class ReservationController {

    private final ReservationCommandService reservationCommandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateReservationResponse createReservation(@RequestBody @Valid CreateReservationRequest request) {
        return ReservationMapper.toCreateReservationResponse(
                reservationCommandService.reserve(
                        request.movieId(),
                        request.scheduleId(),
                        request.seatIds()));
    }

}
