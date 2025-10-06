package services.managers.bookings;

import models.Reservation;
import models.room.Room;
import java.time.LocalDate;
import java.util.List;

/**
 * Defines booking operations available to a regular user.
 * Handles creating, retrieving, and canceling reservations.
 */
public interface BookingManager {
    /**
     * Creates a new reservation for the given room and user.
     *
     * @param userId    the ID of the user making the booking
     * @param room      the room to be booked
     * @param checkIn   check-in date
     * @param checkOut  check-out date
     * @return the newly created {@link Reservation}
     */
    Reservation bookRoom(Integer userId, Room room, LocalDate checkIn, LocalDate checkOut);

    /**
     * Cancels an existing reservation.
     *
     * @param reservationId the ID of the reservation to cancel
     * @return the updated (canceled) {@link Reservation}
     */
    Reservation cancelReservation(Integer reservationId);

    /**
     * Retrieves a reservation by its ID.
     *
     * @param reservationId reservation identifier
     * @return {@link Reservation} if found, otherwise null
     */
    Reservation getReservationById(Integer reservationId);

    /**
     * Returns all active (non-canceled) reservations for a given user.
     *
     * @param userId user identifier
     * @return list of user reservations
     */
    List<Reservation> getAllUserBookingHistory(Integer userId);
}
