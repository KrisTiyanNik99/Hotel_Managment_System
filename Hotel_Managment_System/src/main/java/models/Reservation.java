package models;

import func.FileFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Reservation implements FileFormatter {
    // Тук всички полета са final с цел да направим обекта immutable и да го енкапсулираме напълно
    private final int id;
    private final int userId;
    private final int roomId;
    private final LocalDate arrivalData;
    private final LocalDate departureDate;
    private boolean isCanceled = false;

    public Reservation(int id, int userId, int roomId, LocalDate arrivalData, LocalDate departureDate, boolean isCanceled) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.arrivalData = isValidDate(arrivalData);
        this.departureDate = isValidDate(departureDate);
        this.isCanceled = isCanceled;
    }

    @Override
    public String toFileFormat() {
        return String.format("No:%d; UserID:%d; RoomID:%d; Arrival Data:%s; Departure Data:%s; Is canceled:%s;%n",
                id,
                userId,
                roomId,
                formattedData(arrivalData),
                formattedData(departureDate),
                isCanceled);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomId() {
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

    public void cancelReservation() {
        isCanceled = true;
    }

    // Отделяме тази проверка в собствен метод, защото в бъдеще може да добавяме още валидиращи проверки!
    private LocalDate isValidDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Invalid date format!");
        }

        return date;
    }

    private String formattedData(LocalDate date) {
        return date.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );
    }
}
