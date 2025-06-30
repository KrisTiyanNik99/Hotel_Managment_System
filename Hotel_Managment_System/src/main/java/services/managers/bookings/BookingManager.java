package services.managers.bookings;

import models.Reservation;
import models.room.Room;

import java.time.LocalDate;
import java.util.List;

public interface BookingManager {
    Reservation bookRoom(int userId, Room room, LocalDate checkIn, LocalDate checkOut);
    Reservation cancelReservation(int reservationId);
    Reservation getReservationById(int reservationId);
    List<Reservation> getAllUserBookingHistory(int userId);
    List<Reservation> getCanceledReservations(int userId);
}
