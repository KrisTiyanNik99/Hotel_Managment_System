package controller;

import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.rooms.RoomManager;
import services.managers.users.UserManager;

public class ServiceController implements ManagerController {
    private final BookingManager bookingManager;
    private final RoomTypeManager roomTypeManager;
    private final RoomManager roomManager;
    private final UserManager userManager;

    public ServiceController(BookingManager bookingManager, RoomTypeManager roomTypeManager,
                             RoomManager roomManager, UserManager userManager) {
        this.bookingManager = bookingManager;
        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.userManager = userManager;
    }

    @Override
    public BookingManager getBookingManager() {
        return bookingManager;
    }

    @Override
    public RoomTypeManager getRoomTypeManager() {
        return roomTypeManager;
    }

    @Override
    public RoomManager getRoomManager() {
        return roomManager;
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }
}
