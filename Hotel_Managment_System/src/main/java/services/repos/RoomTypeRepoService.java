package services.repos;

import config.Configurations;
import models.room.RoomType;
import models.room.RoomTypeImpl;

public class RoomTypeRepoService extends RepoService<RoomType> {
    public RoomTypeRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    @Override
    public RoomType findById(Integer id) {
        RoomType copy = super.findById(id);

        return copy == null ? null : new RoomTypeImpl(copy.getId(),
                copy.getName(),
                copy.getAmenities(),
                copy.getMaximumOccupancy());
    }

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
