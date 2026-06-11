package org.unexpected.slience.theater.domain;

public class ScreenMapper {

    private ScreenMapper() {
    }

    public static Screen toScreen(ScreenEntity entity) {
        Screen screen = new Screen();
        screen.setId(entity.getId());
        screen.setName(entity.getName());
        screen.setTotalRows(entity.getTotalRows());
        screen.setTotalCols(entity.getTotalCols());
        screen.setSeatIds(
                entity.getSeats()
                        .stream()
                        .map(SeatEntity::getId)
                        .toList()
        );
        return screen;
    }

    public static Seat toSeat(SeatEntity entity) {
        Seat seat = new Seat();
        seat.setId(entity.getId());
        seat.setSeatNumber(entity.getSeatNumber());
        seat.setSeatRow(entity.getSeatRow());
        return seat;
    }

}
