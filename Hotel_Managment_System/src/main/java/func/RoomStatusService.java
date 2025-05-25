package func;

public interface RoomStatusService {
    void markRoomAsAvailable(int roomId);
    void markRoomAsBooked(int roomId);
}
