package ui.components;

import controller.UIController;
import models.enums.UIElement;
import services.managers.users.UserManager;

import javax.swing.*;
import java.awt.*;

import static config.ConstantMessages.*;
import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public class RegisterPanel extends AbstractsUIElement implements UIComponent {
    private static final int X_SCALE = 240;

    private final UserManager userManager;
    private final UIController controller;

    public RegisterPanel(UserManager userManager, UIController controller) {
        this.userManager = userManager;
        this.controller = controller;

        initComponents();
    }

    private void initComponents() {
        setBackground(Color.CYAN);

        JLabel usernameLabel = new JLabel(USERNAME_TEXT_LABEL);
        setLabelSettings(usernameLabel, 110);

        JTextField usernameTextField = new JTextField();
        setTextFieldSettings(usernameTextField, 145);

        JLabel passwordLabel = new JLabel(PASSWORD_TEXT_LABEL);
        setLabelSettings(passwordLabel, 200);

        JTextField passwordTextField = new JTextField();
        setTextFieldSettings(passwordTextField, 235);

        JButton registerButton = new JButton(REGISTRATION_TEXT_BUTTON);
        registerButton.setBounds(X_SCALE, 295, LABEL_WIDTH, LABEL_HEIGHT + 10);
        registerButton.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        registerButton.setBackground(Color.GREEN);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        add(registerButton);

        setRegisterFunction(registerButton, usernameTextField, passwordTextField);
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.REGISTER;
    }

    private void setLabelSettings(JLabel label, int y_scale) {
        label.setBounds(X_SCALE, y_scale, LABEL_WIDTH, LABEL_HEIGHT);
        label.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        label.setOpaque(false);
        add(label);
    }

    private void setTextFieldSettings(JTextField textField, int y_scale) {
        textField.setBounds(X_SCALE, y_scale, LABEL_WIDTH, LABEL_HEIGHT);
        textField.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.ORANGE);
        add(textField);
    }

    private void setRegisterFunction(JButton registerButton, JTextField usernameTextField, JTextField passwordTextField) {
        registerButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            if ((username.isEmpty() || username.isBlank()) || (password.isEmpty() || password.isBlank())) {
                JOptionPane.showMessageDialog(
                        this,
                        USERNAME_PASSWORD_CANNOT_EMPTY,
                        EMPTY_FIELDS,
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (userManager.isUserExist(username, password)) {
                JOptionPane.showMessageDialog(
                        this,
                        USER_ALREADY_EXIST,
                        EXISTED_USER,
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            try {
                userManager.register(username, password);
                JOptionPane.showMessageDialog(
                        this,
                        SUCCESSFULLY_REGISTRATION,
                        SUCCESS,
                        JOptionPane.INFORMATION_MESSAGE
                );

                controller.showLoginPanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        UNSUCCESSFULLY_REGISTRATION + ex.getMessage(),
                        UNSUCCESSFULLY_TITLE,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
