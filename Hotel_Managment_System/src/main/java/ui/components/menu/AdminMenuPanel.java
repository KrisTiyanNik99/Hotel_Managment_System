package ui.components.menu;

import controller.UIController;
import models.Reservation;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import models.user.User;
import services.managers.bookings.AdminBookingManager;
import services.managers.room_types.AdminRoomTypeManager;
import services.managers.rooms.RoomManager;
import services.managers.users.AdminUserManager;
import ui.components.AbstractsUIElement;

import javax.swing.*;

import java.awt.*;
import java.util.List;

import static config.ConstantMessages.*;
import static config.UIStyle.*;
import static config.UIStyle.DELETE_ROOM;
import static config.UIStyle.FONT_SIZE;

public class AdminMenuPanel extends AbstractsUIElement {
    private static final int X_MENU_SCALE = 10;
    private static final int Y_MENU_SCALE = 50;

    private final AdminRoomTypeManager roomTypeManager;
    private final RoomManager roomManager;
    private final AdminBookingManager bookingManager;
    private final AdminUserManager userManager;

    private final DefaultListModel<RoomType> roomTypeDefaultListModel;
    private final JList<RoomType> roomTypeJList;
    private final DefaultListModel<Room> roomDefaultListModel;
    private final JList<Room> roomJList;
    private final DefaultListModel<Reservation> reservationDefaultListModel;
    private final JList<Reservation> reservationJList;
    private final DefaultListModel<User> userDefaultListModel;
    private final JList<User> userJList;
    private final JComboBox<RoomType> roomTypeJComboBox;

    private JDialog roomTypeDialog;
    private JDialog roomDialog;

    public AdminMenuPanel(UIController controller,
                          AdminRoomTypeManager roomTypeManager,
                          RoomManager roomManager,
                          AdminBookingManager bookingManager,
                          AdminUserManager userManager) {
        super(controller);

        setBackground(Color.DARK_GRAY);

        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
        this.userManager = userManager;

        roomTypeDefaultListModel = new DefaultListModel<>();
        roomTypeJList = new JList<>(roomTypeDefaultListModel);
        roomDefaultListModel = new DefaultListModel<>();
        roomJList = new JList<>(roomDefaultListModel);
        reservationDefaultListModel = new DefaultListModel<>();
        reservationJList = new JList<>(reservationDefaultListModel);
        userDefaultListModel = new DefaultListModel<>();
        userJList = new JList<>(userDefaultListModel);

        roomTypeJComboBox = new JComboBox<>();

        initComponents();
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.ADMIN;
    }

    @Override
    public void initComponents() {
        JLabel adminMessage = new JLabel(ADMIN_HELLO_MESSAGE);
        setMenuLabelSettings(adminMessage, X_MENU_SCALE, 1);

        JLabel roomTypeOptionMenuTitle = new JLabel(AVAILABLE_TYPE_ROOM_TITLE);
        setMenuLabelSettings(roomTypeOptionMenuTitle, X_MENU_SCALE, Y_MENU_SCALE - 20);

        initDialogs();

        loadRoomTypes();
        JScrollPane roomTypeComboBox = new JScrollPane(roomTypeJList);
        roomTypeComboBox.setBounds(X_MENU_SCALE, Y_MENU_SCALE + 10, LABEL_WIDTH + LABEL_WIDTH + 40, LABEL_HEIGHT + 10);
        roomTypeComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomTypeComboBox);

        JButton addRoomTypeButton = new JButton(ADD_ROOM_TYPE);
        setMenuButtonSettings(addRoomTypeButton, X_MENU_SCALE, Y_MENU_SCALE + 65);
        addRoomTypeButton.addActionListener(e -> roomTypeDialog.setVisible(true));
        add(addRoomTypeButton);

        JButton deleteRoomTypeButton = new JButton(DELETE_ROOM_TYPE);
        setMenuButtonSettings(deleteRoomTypeButton, X_MENU_SCALE + 330, Y_MENU_SCALE + 65);
        deleteRoomTypeButton.addActionListener(e -> deleteRoomTypeLogic());
        add(deleteRoomTypeButton);

        JLabel allRoomsLabel = new JLabel(ROOM_LIST);
        setMenuLabelSettings(allRoomsLabel, X_MENU_SCALE, 4 * Y_MENU_SCALE - 40);

        loadRooms();
        JScrollPane roomComboBox = new JScrollPane(roomJList);
        roomComboBox.setBounds(X_MENU_SCALE, 4 * Y_MENU_SCALE - 9, LABEL_WIDTH + LABEL_WIDTH + 40, LABEL_HEIGHT + 10);
        roomComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomComboBox);

        JButton addRoomButton = new JButton(ADD_ROOM);
        setMenuButtonSettings(addRoomButton, X_MENU_SCALE, (4 * Y_MENU_SCALE) + 50);
        addRoomButton.addActionListener(e -> {
            loadRoomTypesToComboBox();
            roomDialog.setVisible(true);
        });

        JButton deleteRoomButton = new JButton(DELETE_ROOM);
        setMenuButtonSettings(deleteRoomButton, X_MENU_SCALE + 330, (4 * Y_MENU_SCALE) + 50);
        deleteRoomButton.addActionListener(e -> deleteRoomLogic());

        JLabel reservationLabel = new JLabel(RESERVATION_LIST);
        setMenuLabelSettings(reservationLabel, X_MENU_SCALE, 6 * Y_MENU_SCALE - 5);

        loadReservation();
        JScrollPane reservationPanel = new JScrollPane(reservationJList);
        reservationPanel.setBounds(X_MENU_SCALE, 6 * Y_MENU_SCALE + 25, LABEL_WIDTH + LABEL_WIDTH + 40, LABEL_HEIGHT + 10);
        reservationPanel.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(reservationPanel);

        JButton deleteReservationButton = new JButton(DELETE_RESERVATION);
        setMenuButtonSettings(deleteReservationButton, X_MENU_SCALE, (6 * Y_MENU_SCALE) + 80);
        deleteReservationButton.addActionListener(e -> deleteReservationLogic());

        JLabel userListLabel = new JLabel(USER_LIST);
        setMenuLabelSettings(userListLabel, X_MENU_SCALE, 8 * Y_MENU_SCALE + 20);

        loadUsers();
        JScrollPane userPanel = new JScrollPane(userJList);
        userPanel.setBounds(X_MENU_SCALE, 9 * Y_MENU_SCALE, LABEL_WIDTH + LABEL_WIDTH + 40, LABEL_HEIGHT + 10);
        userPanel.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(userPanel);

        JButton deleteUserButton = new JButton(DELETE_USER);
        setMenuButtonSettings(deleteUserButton, X_MENU_SCALE, (9 * Y_MENU_SCALE) + 55);
        deleteUserButton.addActionListener(e -> deleteUserLogic());
    }

    private void initDialogs() {
        createRoomTypeDialog();
        createRoomDialog();
    }

    private void loadUsers() {
        userDefaultListModel.clear();
        userManager.getAll()
                .forEach(userDefaultListModel::addElement);

        userJList.revalidate();
        userJList.repaint();
    }

    private void loadReservation() {
        reservationDefaultListModel.clear();
        bookingManager.getAll()
                .forEach(reservationDefaultListModel::addElement);

        reservationJList.revalidate();
        reservationJList.repaint();
    }

    private void loadRoomTypes() {
        roomTypeDefaultListModel.clear();
        roomTypeManager.getAll()
                .forEach(roomTypeDefaultListModel::addElement);

        roomTypeJList.revalidate();
        roomTypeJList.repaint();
    }

    private void loadRooms() {
        roomDefaultListModel.clear();
        roomManager.getAll()
                .forEach(roomDefaultListModel::addElement);

        roomJList.revalidate();
        roomJList.repaint();
    }

    private void deleteUserLogic() {
        User user = userJList.getSelectedValue();
        if (user == null) {
            JOptionPane.showMessageDialog(this, SELECT_USER,
                    EMPTY_USER, JOptionPane.ERROR_MESSAGE);

            return;
        }

        userManager.deleteUser(user);
        loadUsers();

        List<Reservation> userReservations = bookingManager.getAll()
                .stream()
                .filter(res -> res.getUserId().equals(user.getId()))
                .toList();

        userReservations.forEach(bookingManager::deleteReservation);
        loadReservation();
        JOptionPane.showMessageDialog(this, SUCCESSFULLY_DELETE_USER,
                SUCCESSFULLY_DELETE_USER_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteReservationLogic() {
        Reservation deletedReservation = reservationJList.getSelectedValue();
        if (deletedReservation == null) {
            JOptionPane.showMessageDialog(this, RESERVATION_SELECT,
                    RESERVATION_EMPTY, JOptionPane.ERROR_MESSAGE);

            return;
        }

        bookingManager.deleteReservation(deletedReservation);
        JOptionPane.showMessageDialog(this, RESERVATION_DELETED,
                DELETED_RESERVATION, JOptionPane.INFORMATION_MESSAGE);
        loadReservation();
    }

    private void deleteRoomTypeLogic() {
        RoomType deletedRoomType = deleteRoomType();
        loadRoomTypes();
        List<Room> deletedRooms = deleteRooms(deletedRoomType);
        loadRooms();
        deletedRooms.forEach(this::deleteReservationByRoom);
        loadReservation();
    }

    private void deleteRoomLogic() {
        Room room = roomJList.getSelectedValue();
        if (room == null) {
            JOptionPane.showMessageDialog(this, ROOM_NOT_CHOOSE,
                    NO_ROOM_SELECTED_TITLE, JOptionPane.ERROR_MESSAGE);

            return;
        }

        roomManager.deleteRoom(room);
        loadRooms();
        deleteReservationByRoom(room);
        loadReservation();

        JOptionPane.showMessageDialog(this, DELETE_ROOM,
                DELETED_RESERVATION, JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteReservationByRoom(Room room) {
        List<Reservation> reservations = bookingManager.getAll()
                .stream()
                .filter(reservation -> reservation.getRoomId().equals(room.getId()))
                .toList();

        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(this, EMPTY_RESERVATION_LIST,
                    EMPTY_RESERVATION_LIST_TITLE, JOptionPane.ERROR_MESSAGE);

            return;
        }

        reservations.forEach(bookingManager::deleteReservation);
    }

    private RoomType deleteRoomType() {
        RoomType roomType = roomTypeJList.getSelectedValue();
        if (roomType == null) {
            JOptionPane.showMessageDialog(this, SELECT_ROOM_TYPE,
                    NOT_SELECTED_ROOM_TYPE, JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException(SELECT_ROOM_TYPE);
        }
        roomTypeManager.deleteRoomType(roomType);

        return roomType;
    }

    private List<Room> deleteRooms(RoomType roomType) {
        List<Room> roomList = roomManager.getAll().stream()
                .filter(room -> room.getRoomTypeId().equals(roomType.getId()))
                .toList();

        if (roomList.isEmpty()) {
            JOptionPane.showMessageDialog(this, EMPTY_ROOM_LIST,
                    EMPTY_ROOM_LIST_TITLE, JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException(EMPTY_ROOM_LIST);
        }

        for (Room room : roomList) {
            roomManager.deleteRoom(room);
        }

        return roomList;
    }

    private void createRoomDialog() {
        roomDialog = new JDialog(
                parentWindow,
                ROOM_DIALOG_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        roomDialog.setLayout(new GridLayout(4, 2, 10, 10));
        roomDialog.setSize(DIALOG_PANEL_SIZE, DIALOG_PANEL_SIZE);
        roomDialog.setLocationRelativeTo(parentWindow);

        roomDialog.add(new JLabel(ROOM_TYPE_TITLE));
        loadRoomTypesToComboBox();
        roomDialog.add(roomTypeJComboBox);

        roomDialog.add(new JLabel(ROOM_PRICE));
        JTextField pricePerNight = new JTextField();
        roomDialog.add(pricePerNight);

        roomDialog.add(new JLabel(ROOM_CANCEL_FEE));
        JTextField cancelFee = new JTextField();
        roomDialog.add(cancelFee);

        JButton createRoomButton = new JButton(CREATE_ROOM_DIALOG);
        createRoomButton.addActionListener(e -> {
            createRoom(pricePerNight, cancelFee);
            loadRooms();
        });
        roomDialog.add(createRoomButton);

        JButton closeRoomDialogButton = new JButton(CANCEL_ROOM_DIALOG);
        closeRoomDialogButton.addActionListener(e -> roomDialog.dispose());
        roomDialog.add(closeRoomDialogButton);
    }

    private void createRoom(JTextField pricePerNight, JTextField cancelFee) {
        try {
            RoomType roomType = (RoomType) roomTypeJComboBox.getSelectedItem();
            double roomPrice = Double.parseDouble(pricePerNight.getText());
            double roomFee = Double.parseDouble(cancelFee.getText());
            roomManager.createNewRoom(roomType, roomPrice, roomFee);

            JOptionPane.showMessageDialog(this, CREATED_ROOM,
                    NEW_ROOM_ADDED_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(this, PRICE_FORMAT,
                    PRICE_FORMAT_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRoomTypesToComboBox() {
        roomTypeJComboBox.removeAllItems();
        roomTypeManager.getAll()
                .forEach(roomTypeJComboBox::addItem);
    }

    private void createRoomType(JTextField nameField, JTextField amenitiesField, JSpinner occupancySpinner) {
        String name = nameField.getText();
        String amenities = amenitiesField.getText();
        int occupancy = (int) occupancySpinner.getValue();

        if ((name.isEmpty() || name.isBlank()) || (amenities.isEmpty() || amenities.isBlank())) {
            JOptionPane.showMessageDialog(this, EMPTY_FIELD_FOR_ROOM_TYPE,
                    EMPTY_FIELD_ROOM_TYPE_TITLE, JOptionPane.ERROR_MESSAGE);

            return;
        }

        roomTypeManager.createNewRoomType(name, amenities, occupancy);
        JOptionPane.showMessageDialog(this, ADD_NEW_ROOM_TYPE,
                ADD_NEW_ROOM_TYPE_TITLE, JOptionPane.INFORMATION_MESSAGE);
        loadRoomTypes();
    }

    private void createRoomTypeDialog() {
        roomTypeDialog = new JDialog(
                parentWindow,
                ROOM_TYPE_DIALOG_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        roomTypeDialog.setLayout(new GridLayout(4, 4, 10, 10));
        roomTypeDialog.setSize(DIALOG_PANEL_SIZE, DIALOG_PANEL_SIZE);
        roomTypeDialog.setLocationRelativeTo(parentWindow);

        roomTypeDialog.add(new JLabel(ROOM_TYPE_NAME));
        JTextField nameField = new JTextField();
        roomTypeDialog.add(nameField);

        // Amenities
        roomTypeDialog.add(new JLabel(ROOM_TYPE_AMENITIES));
        JTextField amenitiesField = new JTextField();
        roomTypeDialog.add(amenitiesField);

        // Maximum Occupancy
        roomTypeDialog.add(new JLabel(ROOM_TYPE_OCCUPANCY));
        JSpinner occupancySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        roomTypeDialog.add(occupancySpinner);

        roomTypeDialog.add(new JLabel());
        roomTypeDialog.add(new JLabel());

        // Buttons
        JButton createButton = new JButton(ROOM_TYPE_CREATE);
        createButton.addActionListener(e -> createRoomType(nameField, amenitiesField, occupancySpinner));
        roomTypeDialog.add(createButton);

        JButton cancelButton = new JButton(ROOM_TYPE_CANCEL);
        cancelButton.addActionListener(e -> roomTypeDialog.dispose());
        roomTypeDialog.add(cancelButton);
    }
}
