package services.managers.room_types;

import models.room.RoomType;
import models.room.RoomTypeImpl;
import services.repos.RepoService;

import java.util.List;

public class RoomTypeManagerImpl implements RoomTypeManager{
    private final RepoService<RoomType> roomTypeRepository;

    public RoomTypeManagerImpl(RepoService<RoomType> roomTypeRepoService) {
        this.roomTypeRepository = roomTypeRepoService;
    }

    @Override
    public RoomType getById(Integer roomTypeId) {
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

        return getById(roomTypeId);
    }

    public void updateRoomType(RoomType roomType) {
        roomTypeRepository.updateValue(roomType);
    }

    public void deleteRoomType(RoomType roomType) {
        roomTypeRepository.deleteById(roomType.getId());
    }

    @Override
    public List<RoomType> getAll() {
        return roomTypeRepository.findAll();
    }

    @Override
    public List<RoomType> findRoomTypesByName(String typeName) {
        return roomTypeRepository.findAll()
                .stream()
                .filter(e -> typeName.equalsIgnoreCase(e.getName()))
                .toList();
    }
}
