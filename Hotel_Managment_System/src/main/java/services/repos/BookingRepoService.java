package services.repos;

import config.Configurations;
import models.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingRepoService extends RepoService<Reservation> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BookingRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    @Override
    public Reservation findById(int id) {
        Reservation copy = super.findById(id);

        return copy == null ? null : new Reservation(copy.getId(),
                copy.getUserId(),
                copy.getRoomId(),
                copy.getArrivalData(),
                copy.getDepartureDate(),
                copy.isCanceled());
    }

    @Override
    public Reservation getObjectFromData(String[] data) {
        int id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        int userId = Integer.parseInt(data[1].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        int roomId = Integer.parseInt(data[2].split(REGEX_EXPRESSION)[VALUE_POSITION]);
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
