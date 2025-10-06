package func.getter_funcs;

import models.room.Room;
import models.room.RoomType;
import java.util.List;

/**
 * Specialization of {@link DataGetter} for room-related data.
 * Provides higher-level retrieval methods that are specific to room availability
 * and filtering by room type.
 */
public interface RoomDataProvider extends DataGetter<Room> {
    /**
     * Returns all available rooms filtered by a specific {@link RoomType}.
     *
     * @param roomType the room type filter
     * @return list of available rooms of the given type
     */
    List<Room> getAllAvailableRoomsByType(RoomType roomType);
}
