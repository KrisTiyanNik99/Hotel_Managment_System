package ui.components.menu;

import controller.UIController;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import services.managers.bookings.AdminBookingManager;
import services.managers.bookings.BookingManager;
import services.managers.room_types.AdminRoomTypeManager;
import services.managers.rooms.RoomManager;
import ui.components.UserUIElement;

import javax.swing.*;

import java.awt.*;
import java.util.List;

import static config.ConstantMessages.*;
import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public class AdminMenuPanel extends UserUIElement {
    private static final int X_MENU_SCALE = 10;
    private static final int Y_MENU_SCALE = 50;

    private final AdminRoomTypeManager roomTypeManager;
    private final RoomManager roomManager;
    private final AdminBookingManager bookingManager;

    private final DefaultListModel<RoomType> roomTypeDefaultListModel;
    private final JList<RoomType> roomTypeJList;
    private final DefaultListModel<Room> roomDefaultListModel;
    private final JList<Room> roomJList;

    private JDialog roomTypeDialog;
    private JDialog roomDialog;

    public AdminMenuPanel(UIController controller,
                          AdminRoomTypeManager roomTypeManager,
                          RoomManager roomManager,
                          AdminBookingManager bookingManager) {
        super(controller);

        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;

        roomTypeDefaultListModel = new DefaultListModel<>();
        roomTypeJList = new JList<>(roomTypeDefaultListModel);
        roomDefaultListModel = new DefaultListModel<>();
        roomJList = new JList<>(roomDefaultListModel);

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

        JScrollPane roomTypeComboBox = new JScrollPane(roomTypeJList);
        roomTypeComboBox.setBounds(X_MENU_SCALE, Y_MENU_SCALE + 30, LABEL_WIDTH + LABEL_WIDTH + 40, LABEL_HEIGHT + 10);
        roomTypeComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomTypeComboBox);
        loadRoomTypes();

        JButton addRoomTypeButton = new JButton(ADD_ROOM_TYPE);
        setMenuButtonSettings(addRoomTypeButton, X_MENU_SCALE, Y_MENU_SCALE + 90);
        addRoomTypeButton.addActionListener(e -> roomTypeDialog.setVisible(true));
        add(addRoomTypeButton);

        JButton deleteRoomTypeButton = new JButton(DELETE_ROOM_TYPE);
        setMenuButtonSettings(deleteRoomTypeButton, X_MENU_SCALE + 330, Y_MENU_SCALE + 90);
        deleteRoomTypeButton.addActionListener(e -> {
            RoomType deletedRoomType = deleteRoomType();
            List<Room> deletedRooms = deleteRooms(deletedRoomType);
            // Да изтрия всички резервации с тези стаи
        });
        add(deleteRoomTypeButton);

        loadRooms();
        JScrollPane roomComboBox = new JScrollPane(roomJList);
        roomComboBox.setBounds(X_MENU_SCALE, 4 * Y_MENU_SCALE, LABEL_WIDTH + LABEL_WIDTH + 40, LABEL_HEIGHT + 10);
        roomComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomComboBox);

        JButton addRoomButton = new JButton(ADD_ROOM);
        setMenuButtonSettings(addRoomButton, X_MENU_SCALE, (4 * Y_MENU_SCALE) + 90);
        addRoomButton.addActionListener(e -> roomDialog.setVisible(true));

        JButton deleteRoomButton = new JButton(DELETE_ROOM);
        // Трябва да мога да трия потребители
    }

    @Override
    protected void confirmUserReservation() {

    }

    @Override
    public void setUserById(Integer userId) {

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

    private RoomType deleteRoomType() {
        RoomType roomType = roomTypeJList.getSelectedValue();
        if (roomType == null) {
            JOptionPane.showMessageDialog(this, SELECT_ROOM_TYPE,
                    NOT_SELECTED_ROOM_TYPE, JOptionPane.ERROR_MESSAGE);

            throw new RuntimeException(SELECT_ROOM_TYPE);
        }
        roomTypeManager.deleteRoomType(roomType);
        loadRoomTypes();

        return roomType;

        // Трия резаервациите с тази стая

        // Презареждам ui на всички (тип стая, стая, резервации)
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
        loadRooms();

        return roomList;
    }

    private void deleteReservationByRoom(Room room) {

    }

    private void initDialogs() {
        createRoomTypeDialog();
        createRoomDialog();
    }

    private void createRoomDialog() {
        roomDialog = new JDialog(
                parentWindow,
                ROOM_DIALOG_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        roomDialog.setLayout(new GridLayout(5, 2, 10, 10));
        roomDialog.setSize(DIALOG_PANEL_SIZE, DIALOG_PANEL_SIZE);
        roomDialog.setLocationRelativeTo(parentWindow);

        roomDialog.add(new JLabel(ROOM_TYPE_TITLE));
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
        JSpinner occupancySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        roomTypeDialog.add(occupancySpinner);

        // Empty labels for spacing
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
