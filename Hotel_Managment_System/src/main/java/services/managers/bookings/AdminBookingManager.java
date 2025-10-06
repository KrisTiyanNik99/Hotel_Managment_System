package services.managers.bookings;

import models.Reservation;
import java.util.List;

/**
 * Extends {@link BookingManager} with administrative privileges.
 * Allows deletion and full retrieval of all reservations.
 */
public interface AdminBookingManager extends BookingManager {
    /**
     * Permanently deletes a reservation record.
     *
     * @param reservation reservation instance to remove
     */
    void deleteReservation(Reservation reservation);

    /**
     * Retrieves all reservations in the system.
     *
     * @return list of all {@link Reservation} entries
     */
    List<Reservation> getAll();
}
