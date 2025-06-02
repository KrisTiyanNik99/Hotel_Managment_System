package managers.rooms;

import func.Getable;
import models.room.Room;
import models.room.RoomType;

import java.util.List;

public interface RoomManager extends Getable<Room>, RoomStatusService {
    List<Room> getAllAvailableRooms();
    List<Room> getAllAvailableRoomsByType(RoomType roomType);
}
