package org.unexpected.slience.reservation.domain;

public class ReservationMapper {

    private ReservationMapper() {}

    public static Reservation toDomain(ReservationEntity e) {
        Reservation r = new Reservation();
        r.setId(e.getId());
        r.setReservedAt(e.getReservedAt());
        r.setStatus(e.getStatus());
        r.setReservationSeatId(e.getReservationSeats()
                .stream()
                .map(ReservationScheduleSeatEntity::getId)
                .toList()
        );

        r.setExpiresAt(e.getExpiresAt());
        return r;
    }

    public static void updateEntity(Reservation r, ReservationEntity e) {
        e.setReservedAt(r.getReservedAt());
        e.setExpiresAt(r.getExpiresAt());
        e.setStatus(r.getStatus());
    }

}
