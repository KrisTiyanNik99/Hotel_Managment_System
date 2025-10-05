package services.managers.room_types;

import models.room.RoomType;

public interface AdminRoomTypeManager extends RoomTypeManager {
    RoomType createNewRoomType(String name, String amenities, int maximumOccupancy);
    void updateRoomType(RoomType roomType);
    void deleteRoomType(RoomType roomType);
}
