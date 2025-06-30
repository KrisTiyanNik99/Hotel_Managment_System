package services.managers.room_types;

import func.getter_funcs.DataGetter;
import models.room.RoomType;

import java.util.List;

public interface RoomTypeManager extends DataGetter<RoomType> {
    List<RoomType> findRoomTypesByName(String typeName);
}
