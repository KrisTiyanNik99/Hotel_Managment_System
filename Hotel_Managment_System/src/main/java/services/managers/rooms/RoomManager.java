package services.managers.rooms;

import func.getter_funcs.RoomDataProvider;
import models.room.Room;
import models.room.RoomType;

public interface RoomManager extends RoomDataProvider, RoomStatusService {
    void deleteRoom(Room room);
    void updateRoom(Room room);
    Room createNewRoom(RoomType roomType, double pricePerNight, double cancellationFee);
}
