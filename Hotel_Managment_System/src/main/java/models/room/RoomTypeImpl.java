package models.room;

public class RoomTypeImpl implements RoomType {
    private final Integer id;
    private String name;
    private String amenities;
    private int maximumOccupancy;

    public RoomTypeImpl(Integer id, String name, String amenities, int maximumOccupancy) {
        this.id = id;
        this.name = name;
        this.amenities = amenities;
        this.maximumOccupancy = maximumOccupancy;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAmenities() {
        return amenities;
    }

    @Override
    public int getMaximumOccupancy() {
        return maximumOccupancy;
    }

    @Override
    public void changeName(String name) {
        validateString(name, "name");
        this.name = name;
    }

    @Override
    public void changeAmenities(String amenities) {
        validateString(amenities, "amenities");
        this.amenities = amenities;
    }

    @Override
    public void changeMaximumOccupancy(int maximumOccupancy) {
        maximumOccupancy = Math.max(maximumOccupancy, 1);
        this.maximumOccupancy = maximumOccupancy;
    }

    @Override
    public String toString() {
        return String.format("This is %s room", name);
    }

    @Override
    public String textFormat() {
        return String.format("No:%d; Name:%s; Amenities:%s; Maximum Occupancy:%d;%n",
                id,
                name,
                amenities,
                maximumOccupancy);
    }

    private void validateString(String input, String attributes) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Invalid " + attributes);
        }
    }
}
