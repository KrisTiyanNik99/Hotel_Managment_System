package services;

import config.Configurations;
import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;

import java.util.*;

public class RoomRepoService extends RepoService<Room> {
    public RoomRepoService(String fileName) {
        super(fileName);
    }

    @Override
    public Room findById(int id) {
        /*
            Правим override на този метод да връща копие на стойността, защото можем да променяме обекта през референцията.
            Така, когато връщаме копие енкапсулираме логиката и принуждаме потребителя да използва updateValue метода
            за да се променят записите
         */
        Room copy = super.findById(id);

        return copy == null ? null : new RoomImpl(copy.getRoomNumber(),
                copy.getRoomTypeId(),
                copy.getPricePerNight(),
                copy.getCancellationFee(),
                copy.getStatus());
    }

    @Override
    public Room getObjectFromData(String[] data) {
        int roomNumber = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        int roomTypeId = Integer.parseInt(data[1].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        double pricePerNight = Double.parseDouble(data[2].split(REGEX_EXPRESSION)[VALUE_POSITION].replace(",", "."));
        double cancellationFee = Double.parseDouble(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION].replace(",", "."));
        Status status = Status.parseStatus(data[4].split(REGEX_EXPRESSION)[VALUE_POSITION]);

        setNewId(roomNumber);

        return new RoomImpl(roomNumber, roomTypeId, pricePerNight, cancellationFee, status);
    }

    @Override
    public void updateValue(Room room) {
        if (room == null || !existsById(room.getRoomNumber())) {
            System.out.println("Room cannot be null or non existed!");
            return;
        }

        getEntityMap().put(room.getRoomNumber(), room);
        persistToFile();
        System.out.printf("You successfully update %s and save it into file!%n", room.toFileFormat());
    }

    @Override
    public void createValue(Room room) {
        if (room == null || existsById(room.getRoomNumber())) {
            System.out.println("Room cannot be null or already existed!");
            return;
        }

        getEntityMap().put(room.getRoomNumber(), room);
        persistToFile();
        setNewId(room.getRoomNumber());
        System.out.printf("You successfully add new Room into file!%n");
    }


    @Override
    protected void mapDataFromFileLine(Map<Integer, Room> entityMap, String[] sourceObjData) {
        Room room = getObjectFromData(sourceObjData);
        entityMap.put(room.getRoomNumber(), room);
    }

    @Override
    protected String initializeFileName() {
        return Configurations.ROOM_FILE_NAME;
    }
}
