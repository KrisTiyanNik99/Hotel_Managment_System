package services.managers.rooms;

/**
 * Defines room availability operations.
 * Implementations must ensure synchronization between reservation data and room status.
 */
public interface RoomStatusService {
    void markRoomAsAvailable(Integer roomId);
    void markRoomAsBooked(Integer roomId);
}
