package services;

import config.Configurations;
import models.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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
                copy.getDepartureDate());
    }

    @Override
    public Reservation getObjectFromData(String[] data) {
        int id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        int userId = Integer.parseInt(data[1].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        int roomId = Integer.parseInt(data[2].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        LocalDate arrivalData = LocalDate.parse(data[3].split(REGEX_EXPRESSION)[VALUE_POSITION], formatter);
        LocalDate departureDate = LocalDate.parse(data[4].split(REGEX_EXPRESSION)[VALUE_POSITION], formatter);

        return new Reservation(id, userId, roomId, arrivalData, departureDate);
    }

    @Override
    public void updateValue(Reservation reservation) {
        if (reservation == null || !existsById(reservation.getId())) {
            System.out.println("Reservation cannot be null or non existed!");
            return;
        }

        getEntityMap().put(reservation.getId(), reservation);
        persistToFile();
        System.out.println("Reservation is update!");
    }

    @Override
    public void createValue(Reservation reservation) {
        if (reservation == null || existsById(reservation.getId())) {
            System.out.println("Reservation cannot be null or already existed!");
            return;
        }


        getEntityMap().put(reservation.getId(), reservation);
        persistToFile();
        System.out.println("New reservation is added!");
    }

    @Override
    protected void mapDataFromFileLine(Map<Integer, Reservation> entityMap, String[] sourceObjData) {
        Reservation reservation = getObjectFromData(sourceObjData);
        entityMap.put(reservation.getId(), reservation);
    }

    @Override
    protected String initializeFileName() {
        return Configurations.RESERVATION_FILE_NAME;
    }
}
