package managers.room_types;

import func.Getable;
import models.room.RoomType;

import java.util.List;

public interface RoomTypeManager extends Getable<RoomType> {
    List<RoomType> findRoomTypesByName(String typeName);
}
