package ui.components;

import models.enums.UIElement;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.rooms.RoomManager;
import services.managers.users.UserManager;

import javax.swing.*;

import static config.ConstantMessages.*;

public class MenuPanel extends UserUIElement {
    // UI Variables
    //private static final int X_SCALE = ;

    private final BookingManager reservations;
    private final RoomTypeManager roomTypeManager;
    private final RoomManager roomManager;
    private final UserManager userManager;

    private Integer userId;

    // UI Components that will be changed when user is added!
    private JLabel userMessage;

    public MenuPanel(BookingManager reservations, RoomTypeManager roomTypeManager, RoomManager roomManager, UserManager userManager) {
        this.reservations = reservations;
        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.userManager = userManager;

        initComponents();
    }

    @Override
    public void initComponents() {
        userMessage = new JLabel();
        setMenuLabelSettings(userMessage, X_SCALE - 140, 40);


    }

    @Override
    public void setUserId(Integer userId) {
        if (userId == null) {
            JOptionPane.showMessageDialog(
                    this,
                    USER_ID_IS_NULL,
                    USER_ID_NULL_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }

        this.userId = userId;
        // TODO: Добави промени и тогава ревалидация!
        userMessage.setText(String.format(HELLO_USER_MESSAGE, userManager.getUsernameByUserId(userId)));

        //this.removeAll() - само ако искам да променя радикално иначе използвам само долните 2 метода;
        this.revalidate();
        this.repaint();
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.MENU;
    }
}
