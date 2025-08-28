package ui.components;

import controller.UIController;
import func.getter_funcs.RoomDataProvider;
import models.enums.UIElement;
import models.room.RoomType;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.users.UserManager;

import javax.swing.*;

import java.util.List;

import static config.ConstantMessages.*;
import static config.UIStyle.LABEL_HEIGHT;
import static config.UIStyle.LABEL_WIDTH;

public class MenuPanel extends UserUIElement {
    // UI Variables
    private static final int X_MENU_SCALE = 20;

    private final BookingManager reservations;
    private final RoomTypeManager roomTypeManager;
    private final RoomDataProvider roomManager;
    private final UserManager userManager;

    private Integer userId;

    private JLabel userMessage;
    private JList<RoomType> roomTypeJList;

    public MenuPanel(BookingManager reservations, RoomTypeManager roomTypeManager,
                     RoomDataProvider roomManager, UserManager userManager, UIController controller) {
        super(controller);

        this.reservations = reservations;
        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.userManager = userManager;

        initComponents();
    }

    @Override
    public void initComponents() {
        userMessage = new JLabel();
        setMenuLabelSettings(userMessage, X_MENU_SCALE, 10);

        JLabel roomsScrollBarTitle = new JLabel(AVAILABLE_ROOM_TITLE);
        setMenuLabelSettings(roomsScrollBarTitle, X_MENU_SCALE, 100);

        addDataToScrollPane();

        JScrollPane roomTypeScrollPane = new JScrollPane(roomTypeJList);
        roomTypeScrollPane.setBounds(X_MENU_SCALE, 140, LABEL_WIDTH, LABEL_HEIGHT + LABEL_HEIGHT);
        add(roomTypeScrollPane);


        JScrollPane roomScrollPane = new JScrollPane();
        roomScrollPane.setBounds(500,140, LABEL_WIDTH, LABEL_HEIGHT + LABEL_HEIGHT);
        add(roomScrollPane);
    }

    @Override
    public void setUserById(Integer userId) {
        if (userId == null) {
            JOptionPane.showMessageDialog(
                    this,
                    USER_ID_IS_NULL,
                    USER_ID_NULL_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }

        this.userId = userId;

        updateComponents();

        this.revalidate();
        this.repaint();
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.MENU;
    }

    private void updateComponents() {
        userMessage.setText(String.format(HELLO_USER_MESSAGE,
                userManager.getUsernameByUserId(userId)));

        System.out.println("Label text: " + userMessage.getText());
    }

    private void addDataToScrollPane() {
        List<RoomType> roomTypes = roomTypeManager.getAll();
        roomTypeJList = new JList<>(roomTypes.toArray(new RoomType[0]));
        roomTypeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}