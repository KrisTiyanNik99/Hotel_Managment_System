package managers.rooms;

public interface RoomStatusService {
    void markRoomAsAvailable(int roomId);
    void markRoomAsBooked(int roomId);
}
