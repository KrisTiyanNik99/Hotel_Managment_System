package models.room;

import models.enums.Status;

public class RoomImpl implements Room {
    private final Integer roomNumber;
    private Integer roomTypeId;
    private double pricePerNight;
    private double cancellationFee;
    private Status status;

    public RoomImpl(Integer roomNumber, Integer roomTypeId, double pricePerNight, double cancellationFee, Status status) {
        this.roomNumber = roomNumber;
        setRoomTypeId(roomTypeId);
        setPricePerNight(pricePerNight);
        setCancellationFee(cancellationFee);
        this.status = status;
    }

    @Override
    public Integer getId() {
        return roomNumber;
    }

    @Override
    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    @Override
    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public double getCancellationFee() {
        return cancellationFee;
    }

    @Override
    public void setRoomTypeId(int roomTypeId) {
        roomTypeId = Math.max(roomTypeId, 1);
        this.roomTypeId = roomTypeId;
    }

    @Override
    public void setPricePerNight(double pricePerNight) {
        pricePerNight = Math.max(pricePerNight, 9.99);
        this.pricePerNight = pricePerNight;
    }

    @Override
    public void setCancellationFee(double cancellationFee) {
        cancellationFee = Math.max(cancellationFee, 9.99);
        this.cancellationFee = cancellationFee;
    }

    @Override
    public void setStatus(Status tStatus) {
        status = tStatus;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("This is room %d, %.2f per night", roomNumber, pricePerNight);
    }

    @Override
    public String textFormat() {
        return String.format("No:%d; TypeID:%d; Price per night:%.2f; Cancellation Fee:%.2f; Room Status:%s;%n",
                roomNumber,
                roomTypeId,
                pricePerNight,
                cancellationFee,
                status);
    }
}
