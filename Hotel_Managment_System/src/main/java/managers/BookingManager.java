package managers;

import models.Reservation;
import models.enums.Status;
import models.room.Room;
import services.RepoService;

import java.time.LocalDate;
import java.util.List;

public class BookingManager {
    private final RepoService<Reservation> bookingRepo;
    private final RoomManager roomManager;

    public BookingManager(RepoService<Reservation> bookingRepo, RoomManager roomManager) {
        this.bookingRepo = bookingRepo;
        this.roomManager = roomManager;
    }

    public Reservation bookRoom(int userId, Room room, LocalDate checkIn, LocalDate checkOut) {
        int reservationId = bookingRepo.generateNextId();

        bookingRepo.createValue(
                new Reservation(reservationId,
                        userId,
                        room.getRoomNumber(),
                        checkIn,
                        checkOut,
                        false));

        roomManager.markRoomAsBooked(room);

        return getReservationById(reservationId);
    }

    public Reservation cancelReservation(int reservationId) {
        Reservation canceledReservation = getReservationById(reservationId);
        canceledReservation.cancelReservation();
        bookingRepo.updateValue(canceledReservation);

        Room room = roomManager.getRoomById(canceledReservation.getRoomId());

        roomManager.markRoomAsAvailable(room);
        return getReservationById(canceledReservation.getId());
    }

    public List<Reservation> getAllUserBookingHistory(int userId) {
        return bookingRepo.findAll()
                .stream()
                .filter(e -> userId == e.getUserId())
                .toList();
    }

    public List<Reservation> getCanceledReservations(int userId) {
        return bookingRepo.findAll()
                .stream()
                .filter(e -> userId == e.getUserId())
                .filter(Reservation::isCanceled)
                .toList();
    }

    public Reservation getReservationById(int reservationId) {
        return bookingRepo.findById(reservationId);
    }
}
