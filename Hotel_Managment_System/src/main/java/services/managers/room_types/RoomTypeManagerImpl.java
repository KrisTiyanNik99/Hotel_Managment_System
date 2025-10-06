package services.managers.room_types;

import models.room.RoomType;
import models.room.RoomTypeImpl;
import services.repos.RepoService;
import java.util.List;

/**
 * Implementation of {@link AdminRoomTypeManager}.
 * Provides CRUD operations for room type management.
 */
public class RoomTypeManagerImpl implements AdminRoomTypeManager {
    private final RepoService<RoomType> roomTypeRepository;

    public RoomTypeManagerImpl(RepoService<RoomType> roomTypeRepoService) {
        this.roomTypeRepository = roomTypeRepoService;
    }

    @Override
    public RoomType getById(Integer roomTypeId) {
        return roomTypeRepository.findById(roomTypeId);
    }

    @Override
    public RoomType createNewRoomType(String name, String amenities, int maximumOccupancy) {
        // Auto-increment ID simulates SQL-like unique key generation
        int roomTypeId = roomTypeRepository.generateNextId();

        roomTypeRepository.createValue(
                new RoomTypeImpl(roomTypeId, name, amenities, maximumOccupancy)
        );

        return getById(roomTypeId);
    }

    @Override
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
