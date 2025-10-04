package ui.components.menu;

import controller.UIController;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.rooms.RoomManager;
import ui.components.UserUIElement;

import javax.swing.*;

import java.awt.*;

import static config.ConstantMessages.*;
import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public class AdminMenuPanel extends UserUIElement {
    private static final int X_MENU_SCALE = 10;
    private static final int Y_MENU_SCALE = 50;

    private final RoomTypeManager roomTypeManager;
    private final RoomManager roomManager;
    private final BookingManager bookingManager;

    private JComboBox<RoomType> roomTypeComboBox;
    private JComboBox<Room> roomComboBox;
    private JDialog roomTypeDialog;

    public AdminMenuPanel(UIController controller,
                          RoomTypeManager roomTypeManager,
                          RoomManager roomManager,
                          BookingManager bookingManager) {
        super(controller);

        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;

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
        setMenuLabelSettings(roomTypeOptionMenuTitle, X_MENU_SCALE, Y_MENU_SCALE);

        initDialogs();

        roomTypeComboBox = new JComboBox<>();
        roomTypeComboBox.setBounds(X_MENU_SCALE, Y_MENU_SCALE + 30, LABEL_WIDTH, LABEL_HEIGHT);
        roomTypeComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomTypeComboBox);
        loadRoomTypes();

        JButton addRoomTypeButton = new JButton("Add Room Type");
        setMenuButtonSettings(addRoomTypeButton, X_MENU_SCALE, Y_MENU_SCALE + 80);
        addRoomTypeButton.addActionListener(e -> addRoomType());
        add(addRoomTypeButton);

        JButton deleteRoomTypeButton = new JButton("Delete Room Type");
        setMenuButtonSettings(deleteRoomTypeButton, X_MENU_SCALE + 150, Y_MENU_SCALE + 80);
        deleteRoomTypeButton.addActionListener(e -> deleteRoomType());
        add(deleteRoomTypeButton);

    }

    @Override
    protected void confirmUserReservation() {

    }

    @Override
    public void setUserById(Integer userId) {

    }

    private void loadRoomTypes() {
        roomTypeComboBox.removeAllItems();
        roomTypeManager.getAll().forEach(roomTypeComboBox::addItem);
    }

    private void addRoomType() {

    }

    private void deleteRoomType() {
    }

    private void initDialogs() {
        roomTypeDialog = new JDialog(
                parentWindow,
                ROOM_TYPE_DIALOG_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        roomTypeDialog.setLayout(new GridLayout(5, 2, 10, 10));
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
        JSpinner occupancySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        roomTypeDialog.add(occupancySpinner);

        // Empty labels for spacing
        roomTypeDialog.add(new JLabel());
        roomTypeDialog.add(new JLabel());

        // Buttons
        JButton createButton = new JButton(ROOM_TYPE_CANCEL);
        createButton.addActionListener(e -> createRoomType(nextId));
        roomTypeDialog.add(createButton);

        JButton cancelButton = new JButton(ROOM_TYPE_CREATE);
        cancelButton.addActionListener(e -> {
            createdRoomType = null;
            roomTypeDialog.dispose();
        });
        roomTypeDialog.add(cancelButton);

        setVisible(true);
    }
}
