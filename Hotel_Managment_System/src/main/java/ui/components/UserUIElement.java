package ui.components;

import controller.UIController;

import javax.swing.*;
import java.awt.*;

import static config.ConstantMessages.*;
import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public abstract class UserUIElement extends AbstractsUIElement {
    private final JButton confirmReservationButton;
    private final JButton refuseReservationButton;

    protected final JLabel usernameReservationLabel;
    protected final JLabel roomReservationLabel;
    protected final JLabel fromDateReservationLabel;
    protected final JLabel toDateReservationLabel;

    protected JDialog reservationDialog;

    protected UserUIElement(UIController controller) {
        super(controller);

        setBackground(Color.DARK_GRAY);


        usernameReservationLabel = new JLabel(RESERVATION_USER_TITLE_DIALOG);
        roomReservationLabel = new JLabel(RESERVED_ROOM_NUMBER_TITLE_DIALOG);
        fromDateReservationLabel = new JLabel(RESERVATION_FROM_DATE_TITLE_DIALOG);
        toDateReservationLabel = new JLabel(RESERVATION_TO_DATE_TITLE_DIALOG);

        confirmReservationButton = new JButton(CONFIRM_RESERVATION);
        refuseReservationButton = new JButton(CANCEL_RESERVATION);
    }

    protected void initReservationDialog() {
         reservationDialog = new JDialog(
                parentWindow,
                RESERVATION_DIALOG_TITLE,
                Dialog.ModalityType.APPLICATION_MODAL);

        reservationDialog.setSize(DIALOG_PANEL_SIZE, DIALOG_PANEL_SIZE);
        reservationDialog.setLocationRelativeTo(parentWindow);

        JPanel mainLayout = new JPanel(new GridLayout(6, 1, GAP, GAP));
        mainLayout.add(usernameReservationLabel);
        mainLayout.add(roomReservationLabel);
        mainLayout.add(fromDateReservationLabel);
        mainLayout.add(toDateReservationLabel);
        mainLayout.add(confirmReservationButton);
        confirmReservationButton.addActionListener(e -> confirmUserReservation());
        mainLayout.add(refuseReservationButton);
        refuseReservationButton.addActionListener(e -> reservationDialog.dispose());

        reservationDialog.add(mainLayout);
    }

    protected abstract void confirmUserReservation();
    public abstract void setUserById(Integer userId);
}
