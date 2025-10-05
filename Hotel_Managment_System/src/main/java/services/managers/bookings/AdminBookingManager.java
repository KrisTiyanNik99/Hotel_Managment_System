package services.managers.bookings;

import models.Reservation;

import java.util.List;

public interface AdminBookingManager extends BookingManager {
    void deleteReservation(Reservation reservation);
    List<Reservation> getReservationByRoomNumber(Integer roomNumber);
}
