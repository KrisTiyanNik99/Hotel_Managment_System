package interfaces;

import config.Configurations;
import managers.BookingManager;
import managers.RoomManager;
import managers.RoomTypeManager;
import models.Reservation;
import models.room.Room;
import models.room.RoomType;
import models.user.User;
import services.BookingRepoService;
import services.RepoService;
import services.RoomRepoService;
import services.RoomTypeRepoService;

public class BookingConsoleInterface {
    private final RepoService<Room> roomRepoService = new RoomRepoService(Configurations.ROOM_FILE_NAME);
    private final RepoService<RoomType> roomTypeRepoService = new RoomTypeRepoService(Configurations.ROOM_TYPE_FILE_NAME);
    private final RepoService<Reservation> bookingRepoService = new BookingRepoService(Configurations.RESERVATION_FILE_NAME);

    private final RoomManager roomManager;
    private final RoomTypeManager roomTypeManager;
    private final BookingManager bookingManager;
    private final User user;

    public BookingConsoleInterface(User user) {
        this.user = user;

        roomManager = new RoomManager(roomRepoService);
        roomTypeManager = new RoomTypeManager(roomTypeRepoService);
        bookingManager = new BookingManager(bookingRepoService, roomManager);

        start();
    }

    private void start() {
        System.out.println("**********************************************************");
        System.out.println("*              press 1 - View Rooms                      *");
        System.out.println("*              press 2 - Booking Room                    *");
        System.out.println("*              press 3 - Cancel Booking                  *");
        System.out.println("**********************************************************");
    }

    
}
