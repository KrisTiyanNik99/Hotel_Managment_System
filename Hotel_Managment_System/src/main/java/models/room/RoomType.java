package models.room;

import func.Identifiable;

public interface RoomType extends Identifiable {
    String getName();
    String getAmenities();
    int getMaximumOccupancy();
    void changeName(String name);
    void changeAmenities(String amenities);
    void changeMaximumOccupancy(int maximumOccupancy);
}
