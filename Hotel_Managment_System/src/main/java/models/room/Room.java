package models.room;

import func.FileFormatter;
import models.enums.Status;

public interface Room extends FileFormatter {
    int getRoomNumber();
    int getRoomTypeId();
    double getPricePerNight();
    double getCancellationFee();
    void setStatus(Status status);
    void setRoomTypeId(int roomTypeId);
    void setPricePerNight(double pricePerNight);
    void setCancellationFee(double cancellationFee);
    Status getStatus();
}
