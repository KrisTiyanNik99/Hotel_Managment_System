package interfaces;

import config.Configurations;
import managers.BookingManager;
import managers.rooms.RoomManagerImpl;
import managers.room_types.RoomTypeManagerImpl;
import managers.rooms.RoomManager;
import models.Reservation;
import models.room.Room;
import models.room.RoomType;
import models.user.User;
import services.BookingRepoService;
import services.RepoService;
import services.RoomRepoService;
import services.RoomTypeRepoService;

import java.util.List;
import java.util.Scanner;

public class BookingConsoleInterface {
    private final Scanner scanner = new Scanner(System.in);

    private final RoomManager roomManager;
    private final RoomTypeManagerImpl roomTypeManager;
    private final BookingManager bookingManager;
    private final User user;

    public BookingConsoleInterface(User user) {
        this.user = user;

        RepoService<Room> roomRepoService = new RoomRepoService(Configurations.ROOM_FILE_NAME);
        roomManager = new RoomManagerImpl(roomRepoService);

        RepoService<RoomType> roomTypeRepoService = new RoomTypeRepoService(Configurations.ROOM_TYPE_FILE_NAME);
        roomTypeManager = new RoomTypeManagerImpl(roomTypeRepoService);

        RepoService<Reservation> bookingRepoService = new BookingRepoService(Configurations.RESERVATION_FILE_NAME);
        bookingManager = new BookingManager(bookingRepoService, roomManager);

        start();
    }

    private void start() {
        System.out.printf("********************** Hello: %s **********************%n", user.getUsername());
        System.out.println("*                                                        *");
        System.out.println("*              press 1 - View Rooms                      *");
        System.out.println("*              press 2 - Booking Room                    *");
        System.out.println("*              press 3 - Cancel Booking                  *");
        System.out.println("*              press 4 - Exit to login page              *");
        System.out.println("**********************************************************");

        String input = scanner.nextLine();
        while (!"4".equals(input)) {
            input = scanner.nextLine();
            switch (input) {
                case "1" -> viewAllAvailableRooms();
                case "2" -> bookRoom();
                case "3" -> cancelBook();
                case "4" -> exit();
            }
        }
    }

    private void exit() {
    }

    private void cancelBook() {
    }

    private void bookRoom() {
    }

    private void viewAllAvailableRooms() {
        System.out.println("**********      You are in VIEW ROOM page!      **********");
        System.out.println("*                                                        *");
        System.out.println("*        press 1 - Just view all available rooms         *");
        System.out.println("*        press 2 - Search available room by name         *");
        System.out.println("*        press 3 - Exit to main menu                     *");
        System.out.println("**********************************************************");

        String input = scanner.nextLine();
        while (!"3".equals(input)) {
            input = scanner.nextLine();
            switch (input) {
                case "1" -> viewAvailableRooms();
                case "2" -> searchAvailableRoomsByName();
            }
        }
    }

    private void searchAvailableRoomsByName() {
        System.out.println("Type name: ");
        String input = scanner.nextLine();

        System.out.println("Result from searching by name:");
        List<RoomType> roomTypes = roomTypeManager.findRoomTypesByName(input);

    }

    private void viewAvailableRooms() {
        System.out.println();
        System.out.println("**********      Here list of all available rooms!      **********");
        roomManager.getAllAvailableRooms()
                .forEach(e -> {
                    RoomType roomType = roomTypeManager.getById(e.getRoomTypeId());
                    String formattedInfo = String.format("This is room with number %d. Its %s, and have amenities %s.",
                            e.getId(),
                            roomType.getName(),
                            roomType.getAmenities());

                    System.out.println(formattedInfo);
                });
    }
}
