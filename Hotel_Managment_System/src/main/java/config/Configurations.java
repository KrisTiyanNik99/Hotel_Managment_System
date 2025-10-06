package config;

/**
 * Holds application configuration constants related to file system paths and filenames.
 * These constants define where persistent data such as rooms, room types, reservations,
 * and users are stored within the project structure.
 */
public class Configurations {

    /** Root directory for resource files. */
    public static final String FILE_ROOT_PATH =
            "D:\\SirmaAcademy_projects\\Hotel_Managment_System\\src\\main\\resources\\";

    /** File name storing room information. */
    public static final String ROOM_FILE_NAME = "rooms.txt";

    /** File name storing room type definitions. */
    public static final String ROOM_TYPE_FILE_NAME = "roomtypes.txt";

    /** File name storing reservation data. */
    public static final String RESERVATION_FILE_NAME = "bookings.txt";

    /** File name storing user information. */
    public static final String USER_FILE_NAME = "users.txt";
}
