package services.managers.bookings;

import models.Reservation;
import models.room.Room;

import java.time.LocalDate;
import java.util.List;

public interface BookingManager {
    Reservation bookRoom(Integer userId, Room room, LocalDate checkIn, LocalDate checkOut);
    Reservation cancelReservation(Integer reservationId);
    Reservation getReservationById(Integer reservationId);
    List<Reservation> getAllUserBookingHistory(Integer userId);
}
