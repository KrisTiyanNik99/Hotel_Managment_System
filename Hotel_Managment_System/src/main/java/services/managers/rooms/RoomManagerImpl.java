package services.managers.rooms;

import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;
import models.room.RoomType;
import services.repos.RepoService;

import java.util.List;

public class RoomManagerImpl implements RoomManager {
    private final RepoService<Room> roomRepository;

    public RoomManagerImpl(RepoService<Room> roomRepoService) {
        this.roomRepository = roomRepoService;
    }

    @Override
    public Room getById(int roomNumber) {
        return roomRepository.findById(roomNumber);
    }

    @Override
    public Room createNewRoom(RoomType roomType, double pricePerNight, double cancellationFee) {
        int roomNumber = roomRepository.generateNextId();

        // При създаването на нова стая тя трябва да бъде AVAILABLE по подразбиране, докато някой не я наеме чрез метод
        roomRepository.createValue(
                new RoomImpl(roomNumber,
                        roomType.getId(),
                        pricePerNight,
                        cancellationFee,
                        Status.AVAILABLE));

        return getById(roomNumber);
    }

    @Override
    public void updateRoom(Room room) {
        roomRepository.updateValue(room);
    }

    @Override
    public void deleteRoom(Room room) {
        roomRepository.deleteById(room.getId());
    }

    @Override
    public void markRoomAsAvailable(int roomId) {
        markRoom(roomId, Status.AVAILABLE);
    }

    @Override
    public void markRoomAsBooked(int roomId) {
        markRoom(roomId, Status.BOOKED);
    }

    @Override
    public List<Room> getAllAvailableRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(e -> e.getStatus().equals(Status.AVAILABLE))
                .toList();
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAllAvailableRoomsByType(RoomType roomType) {
        return roomRepository.findAll()
                .stream()
                .filter(e -> Status.AVAILABLE.equals(e.getStatus()))
                .filter(e -> roomType.getId() == e.getRoomTypeId())
                .toList();
    }

    private void markRoom(int id, Status status) {
        Room room = getById(id);
        room.setStatus(status);
        updateRoom(room);
    }
}
