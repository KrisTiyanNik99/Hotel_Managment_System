package ui.components;

import controller.UIController;
import func.getter_funcs.RoomDataProvider;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.users.UserManager;

import javax.swing.*;

import java.util.List;

import static config.ConstantMessages.*;
import static config.UIStyle.*;

public class MenuPanel extends UserUIElement {
    private static final int X_MENU_SCALE = 20;
    private static final int Y_MENU_SCALE = 100;

    private final BookingManager reservations;
    private final RoomTypeManager roomTypeManager;
    private final RoomDataProvider roomManager;
    private final UserManager userManager;

    private final DefaultListModel<RoomType> roomTypeListModel;
    private final JList<RoomType> roomTypeJList;
    private final DefaultListModel<Room> roomListModel;
    private final JList<Room> roomJList;

    private Integer userId;

    private JLabel userMessage;

    public MenuPanel(BookingManager reservations, RoomTypeManager roomTypeManager,
                     RoomDataProvider roomManager, UserManager userManager, UIController controller) {
        super(controller);

        this.reservations = reservations;
        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.userManager = userManager;

        roomTypeListModel = new DefaultListModel<>();
        roomTypeJList = new JList<>(roomTypeListModel);
        roomListModel = new DefaultListModel<>();
        roomJList = new JList<>(roomListModel);

        initComponents();
    }

    @Override
    public void initComponents() {
        userMessage = new JLabel();
        setMenuLabelSettings(userMessage, X_MENU_SCALE, 10);

        JLabel roomTypeScrollBarTitle = new JLabel(AVAILABLE_TYPE_ROOM_TITLE);
        setMenuLabelSettings(roomTypeScrollBarTitle, X_MENU_SCALE, Y_MENU_SCALE);

        JScrollPane roomTypeScrollPane = new JScrollPane(roomTypeJList);
        roomTypeScrollPane.setBounds(X_MENU_SCALE, 140, LABEL_WIDTH, LABEL_HEIGHT + LABEL_HEIGHT);
        add(roomTypeScrollPane);

        addRoomTypeDataToJList();

        JButton confirmRoomTypeButton = new JButton(CONFIRM_ROOM_TYPE);
        setMenuButtonSettings(confirmRoomTypeButton, X_MENU_SCALE, Y_MENU_SCALE + 130);
        confirmRoomTypeButton.addActionListener(e -> {
            // TODO: Добави функция когато се потвърди типът на стаята и да ми показва валидните стаи
        });

        JLabel roomScrollBar = new JLabel(AVAILABLE_ROOM_TITLE);
        setMenuLabelSettings(roomScrollBar, X_MENU_SCALE + 440, Y_MENU_SCALE);

        JScrollPane roomScrollPane = new JScrollPane(roomJList);
        roomScrollPane.setBounds(X_MENU_SCALE + 440,140, LABEL_WIDTH, LABEL_HEIGHT + LABEL_HEIGHT);
        add(roomScrollPane);

        JButton conformRoomButton = new JButton(CONFIRM_ROOM);
        setMenuButtonSettings(conformRoomButton, X_MENU_SCALE + 440, Y_MENU_SCALE + Y_MENU_SCALE + 30);
        conformRoomButton.addActionListener(e -> {
            // TODO: Добави функция за отваряне на диалогов прозорец за датите с които ще се създаде резервация!
        });

        // TODO: Да се добави история на резервациите, както и опция да се премахне резервация!

        JButton reservationButton = new JButton(RESERVATION_TITLE);
        setMenuButtonSettings(reservationButton, X_MENU_SCALE, (Y_MENU_SCALE * 4) + 90);
        // TODO: Да излиза диалогов прозорец, който да показва пълната информация на резервацията, която ще бъде направена и
        //  чак тогава чрез потвърждение да се направи!

        JButton toLoginButton = new JButton(TO_LOGIN_TEXT);
        setMenuButtonSettings(toLoginButton, X_MENU_SCALE + 440, (Y_MENU_SCALE * 4) + 90);
        toLoginButton.addActionListener(e -> controller.showLoginPanel());
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

        // TODO: Обновяване на нещата.....
    }

    private void addRoomTypeDataToJList() {
        List<RoomType> roomTypeList = roomTypeManager.getAll();
        roomTypeListModel.clear();

        for (int i = 0; i < roomTypeList.size(); i++) {
            roomTypeListModel.add(i, roomTypeList.get(i));
        }

        roomTypeJList.revalidate();
        roomTypeJList.repaint();
    }
}