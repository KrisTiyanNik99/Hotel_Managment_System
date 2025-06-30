//package ui;
//
//import config.Configurations;
//import services.managers.BookingManager;
//import services.managers.room_types.RoomTypeManager;
//import services.managers.rooms.RoomManagerImpl;
//import services.managers.room_types.RoomTypeManagerImpl;
//import func.getter_funcs.RoomDataProvider;
//import services.managers.rooms.RoomStatusService;
//import models.Reservation;
//import models.room.Room;
//import models.room.RoomType;
//import models.user.User;
//import services.repo.BookingRepoService;
//import services.repo.RepoService;
//import services.repo.RoomRepoService;
//import services.repo.RoomTypeRepoService;
//import services.adapter.RoomDataAdapter;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class BookingConsoleInterface {
//    private final Scanner scanner = new Scanner(System.in);
//
//    private final RoomDataProvider roomManager;
//    private final RoomTypeManager roomTypeManager;
//    private final BookingManager bookingManager;
//
//    private final User user;
//    private final List<Room> savedRooms;
//
//    public BookingConsoleInterface(User user) {
//        this.user = user;
//        savedRooms = new ArrayList<>();
//
//        RepoService<Room> roomRepoService = new RoomRepoService(Configurations.ROOM_FILE_NAME);
//        roomManager = new RoomManagerImpl(roomRepoService);
//        //RoomStatusService roomStatusService = new RoomDataAdapter(roomManager, roomRepoService);
//
//        RepoService<RoomType> roomTypeRepoService = new RoomTypeRepoService(Configurations.ROOM_TYPE_FILE_NAME);
//        roomTypeManager = new RoomTypeManagerImpl(roomTypeRepoService);
//
//        RepoService<Reservation> bookingRepoService = new BookingRepoService(Configurations.RESERVATION_FILE_NAME);
//        //bookingManager = new BookingManager(bookingRepoService, roomStatusService);
//
//        start();
//    }
//
//    private void start() {
//        navigationMenuInfo();
//
//        String input = scanner.nextLine();
//        while (!"4".equals(input)) {
//            navigationMenuInfo();
//            input = scanner.nextLine();
//            switch (input) {
//                case "1" -> viewAllAvailableRooms();
//                case "2" -> bookRoom();
//                case "3" -> cancelBook();
//                case "4" -> exit();
//            }
//        }
//    }
//
//    private void navigationMenuInfo() {
//        System.out.printf("********************** Hello: %s **********************%n", user.getUsername());
//        System.out.println("*                                                        *");
//        System.out.println("*              press 1 - View Rooms                      *");
//        System.out.println("*              press 2 - Booking Room                    *");
//        System.out.println("*              press 3 - Cancel Booking                  *");
//        System.out.println("*              press 4 - Exit to login page              *");
//        System.out.println("**********************************************************");
//    }
//
//    private void exit() {
//    }
//
//    private void cancelBook() {
//        List<Reservation> reservations = bookingManager.getAllUserBookingHistory(user.getId());
//        displayBookInfo(reservations);
//        int size = reservations.size();
//
//        System.out.println("Choose reservation for cancel:");
//        int userNumber = Integer.parseInt(scanner.nextLine());
//        while (userNumber < 1 || userNumber > size) {
//            if (userNumber == size + 1) {
//                return;
//            }
//
//            displayBookInfo(reservations);
//            userNumber = Integer.parseInt(scanner.nextLine());
//        }
//
//        bookingManager.cancelReservation(reservations.get(userNumber).getId());
//    }
//
//    private void displayBookInfo(List<Reservation> reservations) {
//        for (int i = 0; i < reservations.size(); i++) {
//            System.out.println("Press " + i+1 + " for " + reservations.get(i));
//        }
//    }
//
//    private void bookRoom() {
//        if (savedRooms.isEmpty()) {
//            System.out.println("Choose room first from 1 and then 2 menu options");
//            return;
//        }
//
//        for (Room savedRoom : savedRooms) {
//            System.out.println("Choose check in date:");
//            LocalDate checkIn = getLocalDate();
//            System.out.println("Choose check out in date:");
//            LocalDate checkOut = getLocalDate();
//
//            bookingManager.bookRoom(user.getId(), savedRoom, checkIn, checkOut);
//            System.out.println("Reservation is made");
//        }
//    }
//
//    private LocalDate getLocalDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//
//        System.out.println("Input arrival date in format (dd.MM.yyyy):");
//        String dateFormat = scanner.nextLine();
//
//        while (true) {
//            try {
//                return LocalDate.parse(dateFormat, formatter);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//
//    private void viewAllAvailableRooms() {
//        secondNavigationMenuInfo();
//
//        String input = scanner.nextLine();
//        while (!"3".equals(input)) {
//            secondNavigationMenuInfo();
//            input = scanner.nextLine();
//            switch (input) {
//                case "1" -> viewAvailableRooms();
//                case "2" -> searchAvailableRoomsByName();
//            }
//        }
//    }
//
//    private void secondNavigationMenuInfo() {
//        System.out.println("**********      You are in VIEW ROOM page!      **********");
//        System.out.println("*                                                        *");
//        System.out.println("*        press 1 - Just view all available rooms         *");
//        System.out.println("*        press 2 - Search available room by name         *");
//        System.out.println("*        press 3 - Exit to main menu                     *");
//        System.out.println("**********************************************************");
//    }
//
//    private void searchAvailableRoomsByName() {
//        System.out.println("Type name: ");
//        String input = scanner.nextLine();
//
//        System.out.println("Result from searching by name:");
//        List<RoomType> roomTypes = roomTypeManager.findRoomTypesByName(input);
//        List<Room> rooms = getRooms(roomTypes);
//
//        int size = rooms.size();
//        System.out.println("Choose room from 1 to " + size + " or " + size + 1 + " for exit:");
//        int userNumber = Integer.parseInt(scanner.nextLine());
//        while (userNumber < 1 || userNumber > size) {
//            if (userNumber == size + 1) {
//                return;
//            }
//
//            displayInfo(rooms);
//            userNumber = Integer.parseInt(scanner.nextLine());
//        }
//
//        savedRooms.add(rooms.get(userNumber - 1));
//    }
//
//    private void displayInfo(List<Room> rooms) {
//        for (int i = 0; i < rooms.size(); i++) {
//            System.out.println("Press " + i+1 + " for " + rooms.get(i));
//        }
//    }
//
//    private List<Room> getRooms(List<RoomType> roomTypes) {
//        List<Room> rooms = new ArrayList<>();
//
//        for (RoomType roomType : roomTypes) {
//            rooms.addAll(roomManager.getAllAvailableRoomsByType(roomType));
//        }
//
//        return rooms;
//    }
//
//    private void viewAvailableRooms() {
//        System.out.println();
//        System.out.println("**********      Here list of all available rooms!      **********");
//        roomManager.getAllAvailableRooms()
//                .forEach(e -> {
//                    RoomType roomType = roomTypeManager.getById(e.getRoomTypeId());
//                    String formattedInfo = String.format("This is room with number %d. Its %s, and have amenities %s.",
//                            e.getId(),
//                            roomType.getName(),
//                            roomType.getAmenities());
//
//                    System.out.println(formattedInfo);
//                });
//    }
//}
