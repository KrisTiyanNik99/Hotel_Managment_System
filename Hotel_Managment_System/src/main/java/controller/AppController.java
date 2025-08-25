package controller;

import models.Reservation;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import models.user.User;
import services.managers.bookings.BookingManager;
import services.managers.bookings.BookingManagerImpl;
import services.managers.room_types.RoomTypeManager;
import services.managers.room_types.RoomTypeManagerImpl;
import services.managers.rooms.RoomManager;
import services.managers.rooms.RoomManagerImpl;
import services.managers.users.UserManager;
import services.managers.users.UserManagerImpl;
import services.repos.*;
import ui.Window;
import ui.components.*;

public class AppController implements UIController {
    private final Window mainWindow;

    // Instances of all repository classes
    private RepoService<Reservation> reservationRepo;
    private RepoService<RoomType> roomTypeRepo;
    private RepoService<Room> roomRepo;
    private RepoService<User> userRepo;

    // Instances of all manager classes that need a repository classes
    private BookingManager bookingManager;
    private RoomTypeManager roomTypeManager;
    private RoomManager roomManager;
    private UserManager userManager;

    public AppController(Window mainWindow) {
        this.mainWindow = mainWindow;

        createRepositoryInstances();
        createManagerInstances();
    }

    @Override
    public void registerComponents(UIElement component) {
        AbstractsUIElement uiElement;
        if (component.equals(UIElement.LOGIN)) {
            uiElement = new LoginPanel(userManager, this);
        } else if (component.equals(UIElement.REGISTER)) {
            uiElement = new RegisterPanel(userManager, this);
        } else if (component.equals(UIElement.MENU)) {
            uiElement = new MenuPanel(bookingManager, roomTypeManager,
                    roomManager, userManager, this);
        } else {
            throw new IllegalArgumentException();
        }

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

    @Override
    public void showMainPanel(Integer userId) {
        UserUIElement userUIElement = new MenuPanel(
                bookingManager, roomTypeManager, roomManager, userManager, this);
        userUIElement.setUserById(userId);

        mainWindow.replacePanel(UIElement.MENU.getTypeAsString(), userUIElement);
        mainWindow.showPanel(userUIElement.getType());
    }

    @Override
    public void createRepositoryInstances() {
        userRepo = new UserRepoService(null);
        reservationRepo = new BookingRepoService(null);
        roomTypeRepo = new RoomTypeRepoService(null);
        roomRepo = new RoomRepoService(null);
    }

    @Override
    public void createManagerInstances() {
        userManager = new UserManagerImpl(userRepo);
        roomManager = new RoomManagerImpl(roomRepo);
        roomTypeManager = new RoomTypeManagerImpl(roomTypeRepo);
        bookingManager = new BookingManagerImpl(reservationRepo, roomManager);
    }
}
