package services.managers.rooms;

import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;
import models.room.RoomType;
import services.repos.RepoService;
import java.util.List;

/**
 * Implementation of {@link RoomManager}.
 * Manages creation, updates, and availability of rooms.
 */
public class RoomManagerImpl implements RoomManager {
    private final RepoService<Room> roomRepository;

    public RoomManagerImpl(RepoService<Room> roomRepoService) {
        this.roomRepository = roomRepoService;
    }

    @Override
    public Room getById(Integer roomNumber) {
        return roomRepository.findById(roomNumber);
    }

    @Override
    public Room createNewRoom(RoomType roomType, double pricePerNight, double cancellationFee) {
        // Newly created rooms start as AVAILABLE by default
        int roomNumber = roomRepository.generateNextId();

        roomRepository.createValue(
                new RoomImpl(roomNumber, roomType.getId(), pricePerNight, cancellationFee, Status.AVAILABLE)
        );

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
    public void markRoomAsAvailable(Integer roomId) {
        markRoom(roomId, Status.AVAILABLE);
    }

    @Override
    public void markRoomAsBooked(Integer roomId) {
        markRoom(roomId, Status.BOOKED);
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
                .filter(e -> roomType.getId().equals(e.getRoomTypeId()))
                .toList();
    }

    private void markRoom(Integer id, Status status) {
        Room room = getById(id);
        room.setStatus(status);
        updateRoom(room);
    }
}
