import config.Configurations;
import managers.BookingManager;
import managers.RoomManager;
import models.Reservation;
import models.enums.Status;
import models.room.Room;
import models.room.RoomType;
import services.BookingRepoService;
import services.RepoService;
import services.RoomRepoService;
import services.RoomTypeRepoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RepoService<Room> r = new RoomRepoService(Configurations.ROOM_FILE_NAME);
        RepoService<RoomType> rt = new RoomTypeRepoService(Configurations.ROOM_TYPE_FILE_NAME);
        RepoService<Reservation> re = new BookingRepoService(Configurations.RESERVATION_FILE_NAME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        RoomManager rm = new RoomManager(r, rt);
        BookingManager bm = new BookingManager(re, rm);

        List<Reservation> rs = bm.getAllUserBookingHistory(3);
        rs.forEach(System.out::println);
    }

    private static void dosmth3(RepoService<Room> r, RepoService<RoomType> rt) {
//        RoomManager rm = new RoomManager(r, rt);
//
//        RoomType rto = rm.createNewRoomType("Test", "test1, test2, test3", 4);
////        List<RoomType> rtl = rm.getAllRoomTypesByName("test");
////        rtl.forEach(FileFormatter::toFileFormat);
//        rm.createNewRoom(rto, 999.99, 111.11);
////        rto = rm.getRoomTypeById(5);
////        Room nr = rm.createNewRoom(rto, 777.77, 333.33);
////        nr.setStatus(Status.BOOKED);
////        rm.updateRoom(nr);
//
//        Room or1 = rm.getRoomById(1);
//        or1.setStatus(Status.AVAILABLE);
//
//        rm.updateRoom(or1);
    }

    private static void dosmth2() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//        LocalDate date1 = LocalDate.parse("01-05-2025", formatter);
//        LocalDate date2 = LocalDate.parse("30-06-2025", formatter);
//
//        r.createValue(new Reservation(1,8,1,date1,date2));
//        r.createValue(new Reservation(2,5,3,date1,date2));
//        r.createValue(new Reservation(3,4,4,date1,date2));
//        r.createValue(new Reservation(4,3,5,date1,date2));
//        r.createValue(new Reservation(5,2,40,date1,date2));
//        r.createValue(new Reservation(8,1,3,date1,date2));
//
//        r.deleteById(8);
//        r.deleteById(5);
//        r.updateValue(new Reservation(1,8,1,date1,date2));
//
//        Map<Integer, Reservation> rs = r.getEntities();
//
//        rs.entrySet().forEach(entry -> {
//            System.out.printf("%d - > %s %n", entry.getKey(), entry.getValue().toFileFormat());
//        });
    }

    public static void dosmth(){
        //        RoomType ty = new RoomTypeImpl(14, "Test", "neshto si", 4);
//
//        System.out.println();
//        System.out.println("Towa e provaleniq update");
//        r.updateValue(ty);
//        System.out.println();
//        System.out.println("Towa sa uspeshnite updates");
//        RoomType roomType1 = r.findById(1);
//        RoomType roomType2 = r.findById(8);
//        roomType1.changeName("Double");
//        roomType2.changeName("Delux");
//        roomType2.changeAmenities("Two floors, Spiral staircase, Living room, Kitchenette, Two bathrooms, Balcony with view, Flat-screen TVs, Free Wi-Fi");
//        r.updateValue(roomType1);
//        r.updateValue(roomType2);
//        r.deleteById(2);
//        r.deleteById(4);
//        r.deleteById(10);
//        r.createValue(ty);
//        ty = r.findById(ty.getId());
//        ty.changeAmenities("Test34");
//        r.updateValue(ty);
//        System.out.println();
//        List<RoomType> rt = r.findAll();
//        rt.forEach(e -> System.out.println(e.toFileFormat()));
//
//        RoomType roomType3 = r.findById(45);
    }
}
