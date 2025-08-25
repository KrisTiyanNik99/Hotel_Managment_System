package ui.components;

import controller.UIController;
import models.enums.UIElement;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.rooms.RoomManager;
import services.managers.users.UserManager;

import javax.swing.*;

import static config.ConstantMessages.*;

public class MenuPanel extends UserUIElement {
    // UI Variables
    private static final int X_MENU_SCALE = 20;

    private final BookingManager reservations;
    private final RoomTypeManager roomTypeManager;
    private final RoomManager roomManager;
    private final UserManager userManager;

    private Integer userId;

    private JLabel userMessage;

    public MenuPanel(BookingManager reservations, RoomTypeManager roomTypeManager,
                     RoomManager roomManager, UserManager userManager, UIController controller) {
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

    private void updateComponents() {
        userMessage.setText(String.format(HELLO_USER_MESSAGE,
                userManager.getUsernameByUserId(userId)));

        System.out.println("Label text: " + userMessage.getText());
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.MENU;
    }
}
