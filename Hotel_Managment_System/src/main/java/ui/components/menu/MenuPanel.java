package ui.components.menu;

import com.toedter.calendar.JDateChooser;
import controller.UIController;
import func.getter_funcs.RoomDataProvider;
import models.Reservation;
import models.enums.UIElement;
import models.room.Room;
import models.room.RoomType;
import services.managers.bookings.BookingManager;
import services.managers.room_types.RoomTypeManager;
import services.managers.users.UserManager;
import ui.components.UserUIElement;

import javax.swing.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static config.ConstantMessages.*;
import static config.UIStyle.*;

public class MenuPanel extends UserUIElement {
    private static final int X_MENU_SCALE = 20;
    private static final int Y_MENU_SCALE = 100;

    private final BookingManager reservationManager;
    private final RoomTypeManager roomTypeManager;
    private final RoomDataProvider roomManager;
    private final UserManager userManager;
    private final JList<Reservation> reservationJList;
    private final DefaultListModel<Reservation> reservationsHistory;

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

        this.reservationManager = reservations;
        this.roomTypeManager = roomTypeManager;
        this.roomManager = roomManager;
        this.userManager = userManager;

        reservationsHistory = new DefaultListModel<>();
        reservationJList = new JList<>(reservationsHistory);

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
        roomComboBox.setBounds(X_MENU_SCALE + 440, 140, LABEL_WIDTH, LABEL_HEIGHT);
        roomComboBox.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE - 2));
        add(roomComboBox);

        JButton confirmDatesButton = new JButton(CONFIRM_DATE);
        setMenuButtonSettings(confirmDatesButton, X_MENU_SCALE + 440, Y_MENU_SCALE + Y_MENU_SCALE);
        initDateDialog();
        confirmDatesButton.addActionListener(e -> dateDialog.setVisible(true));

        JScrollPane bookingHistory = new JScrollPane(reservationJList);
        bookingHistory.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        bookingHistory.setBounds(X_MENU_SCALE, 260, LABEL_WIDTH + LABEL_WIDTH + 143, LABEL_HEIGHT + 90);
        add(bookingHistory);

        JButton cancelReservationButton = new JButton(CANCEL_RESERVATION);
        setMenuButtonSettings(cancelReservationButton, X_MENU_SCALE + 220, 400);
        cancelReservationButton.addActionListener(e -> cancelReservation());

        JButton reservationButton = new JButton(RESERVATION_TITLE);
        setMenuButtonSettings(reservationButton, X_MENU_SCALE, (Y_MENU_SCALE * 4) + 90);
        initReservationDialog();
        reservationButton.addActionListener(e -> makeReservation());

        JButton toLoginButton = new JButton(TO_LOGIN_TEXT);
        setMenuButtonSettings(toLoginButton, X_MENU_SCALE + 440, (Y_MENU_SCALE * 4) + 90);
        toLoginButton.addActionListener(e -> controller.showLoginPanel());
    }

    @Override
    public void setUserById(Integer userId) {
        if (userId == null) {
            JOptionPane.showMessageDialog(this, USER_ID_IS_NULL,
                    USER_ID_NULL_TITLE, JOptionPane.ERROR_MESSAGE);

            return;
        }

        this.userId = userId;

        updateComponents();
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void confirmUserReservation() {
        Room room = (Room) roomComboBox.getSelectedItem();
        reservationManager.bookRoom(userId, room, arrivalDate, checkOutDate);

        updateUserBookingHistory();
        roomComboBox.removeAllItems();
        reservationDialog.dispose();
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.MENU;
    }

    // #============================== Custom methods for supporting the logic ========================================#
    private void updateComponents() {
        userMessage.setText(String.format(HELLO_USER_MESSAGE,
                userManager.getUsernameByUserId(userId)));

        updateUserBookingHistory();
    }

    private void updateUserBookingHistory() {
        reservationsHistory.clear();
        reservationManager.getAllUserBookingHistory(userId)
                .forEach(reservationsHistory::addElement);

        reservationJList.revalidate();
        reservationJList.repaint();
    }

    private void cancelReservation() {
        Reservation reservation = reservationJList.getSelectedValue();
        if (reservation == null) {
            JOptionPane.showMessageDialog(this, SELECT_RESERVATION,
                    NO_RESERVATION_SELECT, JOptionPane.ERROR_MESSAGE);

            return;
        }

        reservationManager.cancelReservation(reservation.getId());
        updateUserBookingHistory();
    }

    private void addRoomTypeDataToJList() {
        roomTypeComboBox.removeAllItems();
        roomTypeManager.getAll()
                .forEach(e -> roomTypeComboBox.addItem(e));
    }

    private void showValidRooms() {
        RoomType selectedRoomType = (RoomType) roomTypeComboBox.getSelectedItem();
        if (selectedRoomType == null) {
            JOptionPane.showMessageDialog(this, NO_SELECTED_ROOM_TYPE,
                    NO_SELECTED, JOptionPane.ERROR_MESSAGE);

            return;
        }

        List<Room> availableRoomsByType = roomManager.getAllAvailableRoomsByType(selectedRoomType);
        roomComboBox.removeAllItems();
        if (availableRoomsByType.isEmpty()) {
            JOptionPane.showMessageDialog(this, NO_AVAILABLE_ROOMS_TEXT,
                    NO_AVAILABLE_ROOMS_TITLE, JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        availableRoomsByType.forEach(e -> roomComboBox.addItem(e));
    }

    private void makeReservation() {
        usernameReservationLabel.setText(String.format(
                RESERVATION_USER_TITLE_DIALOG, userManager.getUsernameByUserId(userId)));

        Room room = (Room) roomComboBox.getSelectedItem();
        if (room == null) {
            JOptionPane.showMessageDialog(reservationDialog, NO_ROOM_SELECTED,
                    NO_ROOM_SELECTED_TITLE, JOptionPane.ERROR_MESSAGE);

            return;
        }
        roomReservationLabel.setText(String.format(
                RESERVED_ROOM_NUMBER_TITLE_DIALOG, room.getId()
        ));

        if (arrivalDate == null || checkOutDate == null) {
            JOptionPane.showMessageDialog(dateDialog, CHOOSE_DATES,
                    CALENDAR, JOptionPane.ERROR_MESSAGE);

            return;
        }

        fromDateReservationLabel.setText(String.format(
                RESERVATION_FROM_DATE_TITLE_DIALOG, arrivalDate
        ));
        toDateReservationLabel.setText(String.format(
                RESERVATION_TO_DATE_TITLE_DIALOG, checkOutDate
        ));

        reservationDialog.setVisible(true);
    }

    private void initDateDialog() {
        dateDialog = new JDialog(
                parentWindow,
                DATE_CHOOSER_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        dateDialog.setSize(DIALOG_PANEL_SIZE, DIALOG_PANEL_SIZE);
        dateDialog.setLocationRelativeTo(parentWindow);

        JPanel mainLayout = new JPanel(new GridLayout(4, 2, GAP, GAP));
        mainLayout.add(new JLabel(FROM_DATE));

        JDateChooser fromChooser = new JDateChooser();
        fromChooser.setDateFormatString(DATE_FORMAT);
        mainLayout.add(fromChooser);

        mainLayout.add(new JLabel(TO_DATE));
        JDateChooser toChooser = new JDateChooser();
        toChooser.setDateFormatString(DATE_FORMAT);
        mainLayout.add(toChooser);

        JButton okButton = new JButton(OK_TITLE);
        okButton.setFocusPainted(false);
        okButton.setBackground(Color.WHITE);
        okButton.setForeground(Color.BLACK);
        okButton.addActionListener(e -> confirmReservationDates(fromChooser, toChooser));
        mainLayout.add(okButton);

        dateDialog.add(mainLayout);
    }

    private void confirmReservationDates(JDateChooser from, JDateChooser to) {
        Date fromDate = from.getDate();
        Date toDate = to.getDate();

        if (fromDate == null || toDate == null) {
            JOptionPane.showMessageDialog(dateDialog, CHOOSE_DATES, CALENDAR, JOptionPane.ERROR_MESSAGE);

            return;
        }

        arrivalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        checkOutDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        JOptionPane.showMessageDialog(dateDialog, String.format(CHOOSE_DAYS, arrivalDate, checkOutDate),
                CHOOSE_DAYS_TITLE, JOptionPane.INFORMATION_MESSAGE);
        dateDialog.dispose();
    }
}