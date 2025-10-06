package services.managers.rooms;

import func.getter_funcs.RoomDataProvider;
import models.room.Room;
import models.room.RoomType;

/**
 * Extends {@link RoomStatusService} with CRUD operations and data retrieval for rooms.
 */
public interface RoomManager extends RoomDataProvider, RoomStatusService {
    /**
     * Deletes a room record.
     *
     * @param room room instance to delete
     */
    void deleteRoom(Room room);

    /**
     * Updates an existing room entry.
     *
     * @param room updated room entity
     */
    void updateRoom(Room room);

    /**
     * Creates a new room linked to a specific room type.
     *
     * @param roomType         type of room
     * @param pricePerNight    price per night
     * @param cancellationFee  cancellation fee
     * @return created {@link Room}
     */
    Room createNewRoom(RoomType roomType, double pricePerNight, double cancellationFee);
}
