package services;

import config.Configurations;
import models.room.RoomType;
import models.room.RoomTypeImpl;

import java.util.Map;

public class RoomTypeRepoService extends RepoService<RoomType> {
    public RoomTypeRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    @Override
    public RoomType findById(int id) {
        RoomType copy = super.findById(id);

        return copy == null ? null : new RoomTypeImpl(copy.getId(),
                copy.getName(),
                copy.getAmenities(),
                copy.getMaximumOccupancy());
    }

    @Override
    public RoomType getObjectFromData(String[] data) {
        int id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        String name = data[1].split(REGEX_EXPRESSION)[VALUE_POSITION];
        String amenities = data[2].split(REGEX_EXPRESSION)[VALUE_POSITION];
        int maxOccupancy = Integer.parseInt(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION]);

        setNewId(id);

        return new RoomTypeImpl(id, name, amenities, maxOccupancy);
    }

    @Override
    public void updateValue(RoomType roomType) {
        if (roomType == null || !existsById(roomType.getId())) {
            System.out.println("RoomType cannot be null or non existed!");
            return;
        }

        getEntityMap().put(roomType.getId(), roomType);
        persistToFile();
        System.out.printf("You successfully update Room type into file!%n");
    }

    @Override
    public void createValue(RoomType roomType) {
        if (roomType == null || existsById(roomType.getId())) {
            System.out.println("RoomType cannot be null or already existed!");
            return;
        }

        getEntityMap().put(roomType.getId(), roomType);
        persistToFile();
        setNewId(roomType.getId());
        System.out.printf("You successfully add new Room type into file!%n");
    }

    @Override
    protected void mapDataFromFileLine(Map<Integer, RoomType> entityMap, String[] sourceObjData) {
        RoomType roomType = getObjectFromData(sourceObjData);
        entityMap.put(roomType.getId(), roomType);
    }

    @Override
    protected String initializeFileName() {
        return Configurations.ROOM_TYPE_FILE_NAME;
    }
}
