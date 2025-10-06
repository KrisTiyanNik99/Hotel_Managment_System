package services.repos;

import config.Configurations;
import models.enums.Status;
import models.room.Room;
import models.room.RoomImpl;

/**
 * Repository service responsible for managing {@link Room} entities.
 * This class handles reading and writing of room data from/to file-based storage.
 * Each room record is serialized line-by-line and reconstructed via {@link #getObjectFromData(String[])}.
 */
public class RoomRepoService extends RepoService<Room> {

    /**
     * Constructs a {@code RoomRepoService} using the specified repository file.
     *
     * @param fileName the name of the repository file
     */
    public RoomRepoService(String fileName) {
        super(fileName);
    }

    /**
     * Returns a detached copy of a {@link Room} object to preserve encapsulation.
     * Prevents direct mutation of internal map values by ensuring the returned
     * object is a new instance.
     *
     * @param id the room ID
     * @return a copied {@link RoomImpl} instance, or {@code null} if not found
     */
    @Override
    public Room findById(Integer id) {
        Room copy = super.findById(id);
        return copy == null ? null : new RoomImpl(
                copy.getId(),
                copy.getRoomTypeId(),
                copy.getPricePerNight(),
                copy.getCancellationFee(),
                copy.getStatus()
        );
    }

    /**
     * Parses room data from a serialized line and constructs a {@link RoomImpl} instance.
     *
     * @param data the split key-value data array
     * @return a new {@link RoomImpl} object
     */
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
