package services.repos;

import config.Configurations;
import models.room.RoomType;
import models.room.RoomTypeImpl;

/**
 * Repository service responsible for persisting {@link RoomType} entities.
 * Handles serialization and deserialization of room type data for file-based persistence.
 */
public class RoomTypeRepoService extends RepoService<RoomType> {

    /**
     * Constructs a {@code RoomTypeRepoService} using the specified file name.
     *
     * @param repositoryFileName the repository file name
     */
    public RoomTypeRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    /**
     * Returns a deep copy of the stored {@link RoomType} to ensure encapsulation.
     *
     * @param id the room type ID
     * @return a copied {@link RoomTypeImpl} instance, or {@code null} if not found
     */
    @Override
    public RoomType findById(Integer id) {
        RoomType copy = super.findById(id);
        return copy == null ? null : new RoomTypeImpl(
                copy.getId(),
                copy.getName(),
                copy.getAmenities(),
                copy.getMaximumOccupancy()
        );
    }

    /**
     * Converts serialized data into a {@link RoomTypeImpl} instance.
     *
     * @param data the split key-value array
     * @return a new {@link RoomTypeImpl} object
     */
    @Override
    public RoomType getObjectFromData(String[] data) {
        Integer id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        String name = data[1].split(REGEX_EXPRESSION)[VALUE_POSITION];
        String amenities = data[2].split(REGEX_EXPRESSION)[VALUE_POSITION];
        int maxOccupancy = Integer.parseInt(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION]);

        setNewId(id);
        return new RoomTypeImpl(id, name, amenities, maxOccupancy);
    }

    @Override
    protected String typeName() {
        return "Room type";
    }

    @Override
    protected String initializeFileName() {
        return Configurations.ROOM_TYPE_FILE_NAME;
    }
}
