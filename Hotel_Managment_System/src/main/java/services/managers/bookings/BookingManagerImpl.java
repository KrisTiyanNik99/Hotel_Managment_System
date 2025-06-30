package services.managers.bookings;

import services.managers.rooms.RoomStatusService;
import models.Reservation;
import models.room.Room;
import services.repos.RepoService;

import java.time.LocalDate;
import java.util.List;

public class BookingManagerImpl implements BookingManager {
    private final RepoService<Reservation> bookingRepo;
    private final RoomStatusService roomManagerStatus;

    public BookingManagerImpl(RepoService<Reservation> bookingRepo, RoomStatusService roomManagerStatus) {
        this.bookingRepo = bookingRepo;
        this.roomManagerStatus = roomManagerStatus;
    }

    @Override
    public Reservation bookRoom(int userId, Room room, LocalDate checkIn, LocalDate checkOut) {
        /*
        Подаването на стаята като аргумент гарантира, че номера който ще се запази във файла срещу съответната резервация
         ще бъде винаги валиден и съществуващ. Отделно така предотвратяваме подаването на невалидни стойности!
         */
        int reservationId = bookingRepo.generateNextId();

        bookingRepo.createValue(
                new Reservation(reservationId,
                        userId,
                        room.getId(),
                        checkIn,
                        checkOut,
                        false));

        roomManagerStatus.markRoomAsBooked(room.getId());
        return getReservationById(reservationId);
    }

    @Override
    public Reservation cancelReservation(int reservationId) {
        Reservation canceledReservation = getReservationById(reservationId);
        canceledReservation.cancelReservation();
        bookingRepo.updateValue(canceledReservation);

        roomManagerStatus.markRoomAsAvailable(canceledReservation.getRoomId());
        return getReservationById(canceledReservation.getId());
    }

    @Override
    public List<Reservation> getAllUserBookingHistory(int userId) {
        return bookingRepo.findAll()
                .stream()
                .filter(e -> userId == e.getUserId())
                .toList();
    }

    @Override
    public List<Reservation> getCanceledReservations(int userId) {
        return bookingRepo.findAll()
                .stream()
                .filter(e -> userId == e.getUserId())
                .filter(Reservation::isCanceled)
                .toList();
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        return bookingRepo.findById(reservationId);
    }
}
