package services.managers.rooms;

public interface RoomStatusService {
    void markRoomAsAvailable(Integer roomId);
    void markRoomAsBooked(Integer roomId);
}
