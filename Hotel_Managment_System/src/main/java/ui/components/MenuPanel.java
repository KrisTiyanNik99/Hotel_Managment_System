package ui.components;

import com.toedter.calendar.JDateChooser;
import controller.UIController;
import func.getter_funcs.RoomDataProvider;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.users.UserManager;

import javax.swing.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static config.ConstantMessages.*;
import static config.UIStyle.*;

public class MenuPanel extends UserUIElement {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final int X_MENU_SCALE = 20;
    private static final int Y_MENU_SCALE = 100;

    private final BookingManager reservations;
    private final RoomTypeManager roomTypeManager;
    private final RoomDataProvider roomManager;
    private final UserManager userManager;
    private final Window parentWindow;

    private Integer userId;
    private LocalDate arrivalDate;
    private LocalDate checkOutDate;

    private JDialog dateDialog;
    private JLabel userMessage;
    private JComboBox<RoomType> roomTypeComboBox;
    private JComboBox<Room> roomComboBox;

    public MenuPanel(BookingManager reservations, RoomTypeManager roomTypeManager,
                     RoomDataProvider roomManager, UserManager userManager, UIController controller) {
        super(controller);

        this.reservations = reservations;
        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.userManager = userManager;

        parentWindow = SwingUtilities.getWindowAncestor(this);

        initComponents();
    }

    @Override
    public void initComponents() {
        userMessage = new JLabel();
        setMenuLabelSettings(userMessage, X_MENU_SCALE, 10);

        JLabel roomTypeOptionMenuTitle = new JLabel(AVAILABLE_TYPE_ROOM_TITLE);
        setMenuLabelSettings(roomTypeOptionMenuTitle, X_MENU_SCALE, Y_MENU_SCALE);

        roomTypeComboBox = new JComboBox<>();
        roomTypeComboBox.setBounds(X_MENU_SCALE, 140, LABEL_WIDTH, LABEL_HEIGHT);
        roomTypeComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomTypeComboBox);

        addRoomTypeDataToJList();

        JButton confirmRoomTypeButton = new JButton(CONFIRM_ROOM_TYPE);
        setMenuButtonSettings(confirmRoomTypeButton, X_MENU_SCALE, Y_MENU_SCALE + Y_MENU_SCALE);
        confirmRoomTypeButton.addActionListener(e -> showValidRooms());

        JLabel roomOptionMenuTitle = new JLabel(AVAILABLE_ROOM_TITLE);
        setMenuLabelSettings(roomOptionMenuTitle, X_MENU_SCALE + 440, Y_MENU_SCALE);

        roomComboBox = new JComboBox<>();
        roomComboBox.setBounds(X_MENU_SCALE + 440,140, LABEL_WIDTH, LABEL_HEIGHT);
        roomComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomComboBox);

        JButton confirmDatesButton = new JButton(CONFIRM_DATE);
        setMenuButtonSettings(confirmDatesButton, X_MENU_SCALE + 440, Y_MENU_SCALE + Y_MENU_SCALE);
        initDateDialog();
        // TODO: Добави функция за отваряне на диалогов прозорец за датите с които ще се създаде резервация!
        confirmDatesButton.addActionListener(e -> showDateDialog() );

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

    // #============================== Custom methods for supporting the logic ========================================#
    private void updateComponents() {
        userMessage.setText(String.format(HELLO_USER_MESSAGE,
                userManager.getUsernameByUserId(userId)));

        // TODO: Обновяване на нещата.....
    }

    private void addRoomTypeDataToJList() {
        List<RoomType> roomTypeList = roomTypeManager.getAll();
        roomTypeComboBox.removeAllItems();

        for (RoomType roomType : roomTypeList) {
            roomTypeComboBox.addItem(roomType);
        }
    }

    private void initDateDialog() {
        dateDialog = new JDialog(
                parentWindow,
                DATE_CHOOSER_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        dateDialog.setSize(400, 400);
        dateDialog.setLocationRelativeTo(parentWindow);

        JPanel mainLayout = new JPanel(new GridLayout(3,2,GAP,GAP));
        mainLayout.add(new JLabel(FROM_DATE));

        JDateChooser fromChooser = new JDateChooser();
        fromChooser.setDateFormatString(DATE_FORMAT);
        mainLayout.add(fromChooser);

        JDateChooser toChooser = new JDateChooser();
        toChooser.setDateFormatString(DATE_FORMAT);
        mainLayout.add(toChooser);

        JButton okButton = new JButton(OK_TITLE);
        okButton.addActionListener(e -> {
            String t1 = fromChooser.getDate().toString();
            String t2 = toChooser.getDate().toString();

            System.out.println(t1);
            System.out.println(t2);
            //arrivalDate = fromChooser.getDate();
            //checkOutDate = toChooser.getDate();

            if (arrivalDate == null || checkOutDate == null) {
                JOptionPane.showMessageDialog(
                        dateDialog,
                        CHOOSE_DATES,
                        CALENDAR,
                        JOptionPane.ERROR_MESSAGE);

                return;
            }
        });

        dateDialog.add(mainLayout);
    }

    private void showValidRooms() {
        RoomType selectedRoomType = (RoomType) roomTypeComboBox.getSelectedItem();
        if (selectedRoomType == null) {
            JOptionPane.showMessageDialog(
                    this,
                    NO_SELECTED_ROOM_TYPE,
                    NO_SELECTED,
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        List<Room> availableRoomsByType = roomManager.getAllAvailableRoomsByType(selectedRoomType);
        roomComboBox.removeAllItems();
        if (availableRoomsByType.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    NO_AVAILABLE_ROOMS_TEXT,
                    NO_AVAILABLE_ROOMS_TITLE,
                    JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        for (Room room : availableRoomsByType) {
            System.out.println(room);
            roomComboBox.addItem(room);
        }
    }

    private void showDateDialog() {
        dateDialog.setVisible(true);
    }
}