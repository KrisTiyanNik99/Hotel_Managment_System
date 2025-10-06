package models.room;

import func.Identifiable;
import models.enums.Status;

/**
 * Represents a hotel room entity.
 * Each room is identified by its number and linked to a specific room type.
 */
public interface Room extends Identifiable {
    Integer getRoomTypeId();
    double getPricePerNight();
    double getCancellationFee();
    void setStatus(Status status);
    void setRoomTypeId(int roomTypeId);
    void setPricePerNight(double pricePerNight);
    void setCancellationFee(double cancellationFee);
    Status getStatus();
}
