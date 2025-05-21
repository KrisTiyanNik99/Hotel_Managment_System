package models.room;

import func.FileFormatter;

public interface RoomType extends FileFormatter {
    int getId();
    String getName();
    String getAmenities();
    int getMaximumOccupancy();
    void changeName(String name);
    void changeAmenities(String amenities);
    void changeMaximumOccupancy(int maximumOccupancy);
}
