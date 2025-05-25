package managers;

import models.room.RoomType;
import models.room.RoomTypeImpl;
import services.RepoService;

import java.util.List;

public class RoomTypeManager {
    private final RepoService<RoomType> roomTypeRepository;

    public RoomTypeManager(RepoService<RoomType> roomTypeRepoService) {
        this.roomTypeRepository = roomTypeRepoService;
    }

    public RoomType getRoomTypeById(int roomTypeId) {
        return roomTypeRepository.findById(roomTypeId);
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
}
