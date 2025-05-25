package managers;

import func.RoomStatusService;
import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;
import models.room.RoomType;
import services.RepoService;

import java.util.List;

public class RoomManager implements RoomStatusService {
    private final RepoService<Room> roomRepository;

    public RoomManager(RepoService<Room> roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomById(int roomNumber) {
        return roomRepository.findById(roomNumber);
    }

    public Room createNewRoom(RoomType roomType, double pricePerNight, double cancellationFee) {
        int roomNumber = roomRepository.generateNextId();

        // При създаването на нова стая тя трябва да бъде AVAILABLE по подразбиране, докато някой не я наеме чрез метод
        roomRepository.createValue(
                new RoomImpl(roomNumber,
                        roomType.getId(),
                        pricePerNight,
                        cancellationFee,
                        Status.AVAILABLE));

        return getRoomById(roomNumber);
    }

    @Override
    public void markRoomAsBooked(int roomId) {
        Room room = getRoomById(roomId);
        room.setStatus(Status.BOOKED);
        updateRoom(room);
    }

    @Override
    public void markRoomAsAvailable(int roomId) {
        Room room = getRoomById(roomId);
        room.setStatus(Status.AVAILABLE);
        updateRoom(room);
    }

    public void updateRoom(Room room) {
        roomRepository.updateValue(room);
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(e -> e.getStatus().equals(Status.AVAILABLE))
                .toList();
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAllAvailableRoomsByType(RoomType roomType) {
        return roomRepository.findAll()
                .stream()
                .filter(e -> Status.AVAILABLE.equals(e.getStatus()))
                .filter(e -> roomType.getId() == e.getRoomTypeId())
                .toList();
    }

    public void deleteRoom(Room room) {
        roomRepository.deleteById(room.getId());
    }
}
