package services.managers.room_types;

import models.room.RoomType;

/**
 * Extends {@link RoomTypeManager} with CRUD operations for administrators.
 */
public interface AdminRoomTypeManager extends RoomTypeManager {
    /**
     * Creates a new room type entry.
     *
     * @param name              type name
     * @param amenities         type amenities
     * @param maximumOccupancy  maximum number of guests
     * @return newly created {@link RoomType}
     */
    RoomType createNewRoomType(String name, String amenities, int maximumOccupancy);

    /**
     * Deletes an existing room type record.
     *
     * @param roomType room type to remove
     */
    void deleteRoomType(RoomType roomType);
}
