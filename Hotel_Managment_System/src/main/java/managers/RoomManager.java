package managers;

import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;
import models.room.RoomType;
import models.room.RoomTypeImpl;
import services.RepoService;

import java.util.List;

public class RoomManager {
    private final RepoService<Room> roomRepository;
    private final RepoService<RoomType> roomTypeRepository;

    public RoomManager(RepoService<Room> roomRepository, RepoService<RoomType> roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    public RoomType createNewRoomType(String name, String amenities, int maximumOccupancy) {
        // При създаването на нова стая правил автоматичен брояч, наподобяващ уникалните ключове в sql
        int roomTypeId = roomTypeRepository.generateNextId();
        roomTypeRepository.createValue(
                new RoomTypeImpl(roomTypeId,
                        name,
                        amenities,
                        maximumOccupancy));

        return getRoomTypeById(roomTypeId);
    }

    public void updateRoomType(RoomType roomType) {
        roomTypeRepository.updateValue(roomType);
    }

    public RoomType getRoomTypeById(int roomTypeId) {
        return roomTypeRepository.findById(roomTypeId);
    }

    public void deleteRoomType(RoomType roomType) {
        roomTypeRepository.deleteById(roomType.getId());
    }

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public List<RoomType> findRoomTypeByName(String typeName) {
        return roomTypeRepository.findAll()
                .stream()
                .filter(e -> typeName.equalsIgnoreCase(e.getName()))
                .toList();
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

    public void markRoomAsBooked(Room room) {
        room.setStatus(Status.BOOKED);
        updateRoom(room);
    }

    public void markRoomAsAvailable(Room room) {
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

    public List<Room> getAllAvailableRoomsByTypeName(String roomTypeName) {
        List<Integer> matchingTypeIds = findRoomTypeByName(roomTypeName)
                .stream()
                .map(RoomType::getId)
                .toList();

        return roomRepository.findAll()
                .stream()
                .filter(r -> matchingTypeIds.contains(r.getRoomTypeId()))
                .filter(r -> Status.AVAILABLE.equals(r.getStatus()))
                .toList();
    }

    public Room getRoomById(int roomNumber) {
        return roomRepository.findById(roomNumber);
    }

    public void deleteRoom(Room room) {
        roomRepository.deleteById(room.getRoomNumber());
    }
}
