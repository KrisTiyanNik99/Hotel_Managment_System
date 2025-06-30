package func.getter_funcs;

import models.room.Room;
import models.room.RoomType;

import java.util.List;

public interface RoomDataProvider extends DataGetter<Room> {
    List<Room> getAllAvailableRooms();
    List<Room> getAllAvailableRoomsByType(RoomType roomType);
}
