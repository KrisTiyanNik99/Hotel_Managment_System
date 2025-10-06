package controller;

import models.Reservation;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import models.user.User;
import services.managers.bookings.AdminBookingManager;
import services.managers.bookings.BookingManagerImpl;
import services.managers.room_types.AdminRoomTypeManager;
import services.managers.room_types.RoomTypeManagerImpl;
import services.managers.rooms.RoomManager;
import services.managers.rooms.RoomManagerImpl;
import services.managers.users.AdminUserManager;
import services.managers.users.UserManagerImpl;
import services.repos.*;
import ui.Window;
import ui.components.*;
import ui.components.auth.LoginPanel;
import ui.components.auth.RegisterPanel;
import ui.components.menu.AdminMenuPanel;
import ui.components.menu.MenuPanel;

/**
 * The main application controller responsible for:
 *   Initializing repositories and managers
 *   Registering and managing UI components
 *   Handling navigation between different application panels
 * Implements {@link UIController} and serves as the central coordination
 * point between the data layer (repositories/managers) and the UI layer.
 */
public class AppController implements UIController {

    /** The main application window that hosts all UI panels. */
    private final Window mainWindow;

    // --- Repository instances ---
    private RepoService<Reservation> reservationRepo;
    private RepoService<RoomType> roomTypeRepo;
    private RepoService<Room> roomRepo;
    private RepoService<User> userRepo;

    // --- Manager instances ---
    private AdminBookingManager bookingManager;
    private AdminRoomTypeManager roomTypeManager;
    private RoomManager roomManager;
    private AdminUserManager userManager;

    /**
     * Constructs a new {@code AppController} and initializes
     * all repository and manager instances.
     *
     * @param mainWindow the main application window reference
     */
    public AppController(Window mainWindow) {
        this.mainWindow = mainWindow;
        createRepositoryInstances();
        createManagerInstances();
    }

    /**
     * Registers the given UI component type and adds it to the main window.
     *
     * @param component the type of UI element to register
     * @throws IllegalArgumentException if the component type is not supported
     */
    @Override
    public void registerComponents(UIElement component) {
        AbstractsUIElement uiElement;

        // Determine which UI component to create based on the element type
        if (component.equals(UIElement.LOGIN)) {
            uiElement = new LoginPanel(userManager, this);
        } else if (component.equals(UIElement.REGISTER)) {
            uiElement = new RegisterPanel(userManager, this);
        } else if (component.equals(UIElement.MENU)) {
            uiElement = new MenuPanel(bookingManager, roomTypeManager, roomManager, userManager, this);
        } else if (component.equals(UIElement.ADMIN)) {
            uiElement = new AdminMenuPanel(this, roomTypeManager, roomManager, bookingManager, userManager);
        } else {
            throw new IllegalArgumentException("Unsupported UI component type: " + component);
        }

        // Register the created panel with the main window
        mainWindow.registerPanel(uiElement.getType(), uiElement);
    }

    @Override
    public void showLoginPanel() {
        mainWindow.showPanel(UIElement.LOGIN.getTypeAsString());
    }

    @Override
    public void showRegisterPanel() {
        mainWindow.showPanel(UIElement.REGISTER.getTypeAsString());
    }

    /**
     * Creates a new instance of {@link MenuPanel} for the given user,
     * updates it with user-specific data, replaces the previous panel,
     * and displays it.
     */
    @Override
    public void showMainPanel(Integer userId) {
        UserUIElement userUIElement = new MenuPanel(
                bookingManager,
                roomTypeManager,
                roomManager,
                userManager,
                this
        );

        // Bind user data to the panel before showing it
        userUIElement.setUserById(userId);

        mainWindow.replacePanel(UIElement.MENU.getTypeAsString(), userUIElement);
        mainWindow.showPanel(userUIElement.getType());
    }

    /**
     * Instantiates a new {@link AdminMenuPanel} and loads it in the main window.
     */
    @Override
    public void showAdminPanel() {
        AbstractsUIElement adminPanel = new AdminMenuPanel(
                this,
                roomTypeManager,
                roomManager,
                bookingManager,
                userManager
        );

        mainWindow.replacePanel(UIElement.ADMIN.getTypeAsString(), adminPanel);
        mainWindow.showPanel(UIElement.ADMIN.getTypeAsString());
    }

    /**
     * Initializes all repository instances used across managers.
     * Each repository handles persistent data operations for a specific entity.
     */
    @Override
    public void createRepositoryInstances() {
        userRepo = new UserRepoService(null);
        reservationRepo = new BookingRepoService(null);
        roomTypeRepo = new RoomTypeRepoService(null);
        roomRepo = new RoomRepoService(null);
    }

    /**
     * Initializes all manager instances responsible for handling
     * business logic and communicating with repositories.
     */
    @Override
    public void createManagerInstances() {
        userManager = new UserManagerImpl(userRepo);
        roomManager = new RoomManagerImpl(roomRepo);
        roomTypeManager = new RoomTypeManagerImpl(roomTypeRepo);
        bookingManager = new BookingManagerImpl(reservationRepo, roomManager);
    }
}
