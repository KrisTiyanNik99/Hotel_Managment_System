package models;

import func.Identifiable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Immutable representation of a reservation record.
 * Each reservation is tied to a user, a room, and specific date range.
 */
public final class Reservation implements Identifiable {
    private final Integer id;
    private final int userId;
    private final int roomId;
    private final LocalDate arrivalData;
    private final LocalDate departureDate;
    private boolean isCanceled = false;

    public Reservation(Integer id, Integer userId, Integer roomId, LocalDate arrivalData, LocalDate departureDate, boolean isCanceled) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.arrivalData = isValidDate(arrivalData);
        this.departureDate = isValidDate(departureDate);
        this.isCanceled = isCanceled;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public LocalDate getArrivalData() {
        return arrivalData;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * Marks this reservation as canceled.
     */
    public void cancelReservation() {
        isCanceled = true;
    }

    private LocalDate isValidDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Invalid date format!");
        }
        return date;
    }

    private String formattedData(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public String toString() {
        return String.format("Reservation from %s to %s", arrivalData, departureDate);
    }

    @Override
    public String textFormat() {
        return String.format("No:%d; UserID:%d; RoomID:%d; Arrival Data:%s; Departure Data:%s; Is canceled:%s;%n",
                id, userId, roomId, formattedData(arrivalData), formattedData(departureDate), isCanceled);
    }
}
