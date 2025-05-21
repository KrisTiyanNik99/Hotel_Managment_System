import models.Reservation;
import services.BookingRepoService;
import services.RepoService;

public class Main {
    public static void main(String[] args) {
        RepoService<Reservation> r = new BookingRepoService(null);


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
