package services.repos;

import config.Configurations;
import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;

public class RoomRepoService extends RepoService<Room> {
    public RoomRepoService(String fileName) {
        super(fileName);
    }

    @Override
    public Room findById(Integer id) {
        /*
            Правим override на този метод да връща копие на стойността, защото можем да променяме обекта през референцията.
            Така, когато връщаме копие енкапсулираме логиката и принуждаме потребителя да използва updateValue метода
            за да се променят записите
         */
        Room copy = super.findById(id);

        return copy == null ? null : new RoomImpl(copy.getId(),
                copy.getRoomTypeId(),
                copy.getPricePerNight(),
                copy.getCancellationFee(),
                copy.getStatus());
    }

    @Override
    public Room getObjectFromData(String[] data) {
        Integer roomNumber = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        Integer roomTypeId = Integer.parseInt(data[1].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        double pricePerNight = Double.parseDouble(data[2].split(REGEX_EXPRESSION)[VALUE_POSITION].replace(",", "."));
        double cancellationFee = Double.parseDouble(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION].replace(",", "."));
        Status status = Status.parseStatus(data[4].split(REGEX_EXPRESSION)[VALUE_POSITION]);

        setNewId(roomNumber);

        return new RoomImpl(roomNumber, roomTypeId, pricePerNight, cancellationFee, status);
    }

    @Override
    protected String typeName() {
        return "Room";
    }

    @Override
    protected String initializeFileName() {
        return Configurations.ROOM_FILE_NAME;
    }
}
