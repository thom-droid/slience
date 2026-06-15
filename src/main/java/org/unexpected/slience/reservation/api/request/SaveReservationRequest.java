package org.unexpected.slience.reservation.api.request;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;

public record SaveReservationRequest(
        @NotNull Long scheduleId,
        @NotNull Long movieId,
        @NotNull Long screenId,
        @NotNull Collection<Long> seatIds
) {
}
