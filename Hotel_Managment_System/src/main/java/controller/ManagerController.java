package controller;

import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.rooms.RoomManager;
import services.managers.users.UserManager;

public interface ManagerController {
    BookingManager getBookingManager();
    RoomTypeManager getRoomTypeManager();
    RoomManager getRoomManager();
    UserManager getUserManager();
}
