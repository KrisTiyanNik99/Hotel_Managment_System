package services.managers.room_types;

import func.getter_funcs.DataGetter;
import models.room.RoomType;
import java.util.List;

/**
 * Provides read-only access to room types.
 */
public interface RoomTypeManager extends DataGetter<RoomType> {
    /**
     * Finds room types that match the given name (case-insensitive).
     *
     * @param typeName room type name
     * @return list of matching room types
     */
    List<RoomType> findRoomTypesByName(String typeName);
}
