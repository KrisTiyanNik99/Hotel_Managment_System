package services.repos;

import config.Configurations;
import models.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Repository service responsible for handling persistence of {@link Reservation} entities.
 * Provides methods for reading and writing reservation data to a file-based storage system.
 * Each reservation record is stored in a serialized line format and reconstructed using
 * {@link #getObjectFromData(String[])}.
 */
public class BookingRepoService extends RepoService<Reservation> {

    /** Formatter used for serializing and parsing date values in reservation data. */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Constructs a new {@code BookingRepoService} for the specified file.
     *
     * @param repositoryFileName the name of the repository file
     */
    public BookingRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    /**
     * Retrieves a deep copy of the reservation by ID.
     * Overridden to prevent direct modification of internal objects by returning
     * a detached copy.
     *
     * @param id the reservation ID
     * @return a new copy of the {@link Reservation}, or {@code null} if not found
     */
    @Override
    public Reservation findById(Integer id) {
        Reservation copy = super.findById(id);
        return copy == null ? null : new Reservation(
                copy.getId(),
                copy.getUserId(),
                copy.getRoomId(),
                copy.getArrivalData(),
                copy.getDepartureDate(),
                copy.isCanceled()
        );
    }

    /**
     * Converts a line of serialized reservation data into a {@link Reservation} object.
     *
     * @param data the split string array containing key-value pairs
     * @return a reconstructed {@link Reservation} instance
     */
    @Override
    public Reservation getObjectFromData(String[] data) {
        Integer id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        Integer userId = Integer.parseInt(data[1].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        Integer roomId = Integer.parseInt(data[2].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        LocalDate arrivalData = LocalDate.parse(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION], formatter);
        LocalDate departureDate = LocalDate.parse(data[4].split(REGEX_EXPRESSION)[VALUE_POSITION], formatter);
        boolean isCanceled = data.length == 6 && Boolean.parseBoolean(data[5].split(REGEX_EXPRESSION)[VALUE_POSITION]);

        setNewId(id);
        return new Reservation(id, userId, roomId, arrivalData, departureDate, isCanceled);
    }

    @Override
    protected String typeName() {
        return "Reservation";
    }

    @Override
    protected String initializeFileName() {
        return Configurations.RESERVATION_FILE_NAME;
    }
}
