package models.room;

import func.Identifiable;

/**
 * Represents a specific type of room, including its name,
 * amenities and maximum occupancy.
 */
public interface RoomType extends Identifiable {
    String getName();
    String getAmenities();
    int getMaximumOccupancy();
    void changeName(String name);
    void changeAmenities(String amenities);
    void changeMaximumOccupancy(int maximumOccupancy);
}
