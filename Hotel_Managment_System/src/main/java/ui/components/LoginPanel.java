package ui.components;

import controller.UIController;
import models.enums.UIElement;
import models.user.User;
import services.managers.users.UserManager;

import javax.swing.*;
import java.awt.*;

import static config.ConstantMessages.USERNAME_PASSWORD_CANNOT_EMPTY;
import static config.ConstantMessages.USER_DOES_NOT_EXIST;
import static config.UIStyle.*;

public class LoginPanel extends AbstractsUIElement implements UIComponent {
    private static final int X_SCALE = 240;

    private final UserManager userManager;
    private final UIController controller;

    public LoginPanel(UserManager userManager, UIController controller) {
        this.userManager = userManager;
        this.controller = controller;

        initComponents();
    }

    private void initComponents() {
        setBackground(Color.CYAN);

        JLabel usernameLabel = new JLabel(USERNAME_TEXT_LABEL);
        setLabelSettings(usernameLabel, 100);

        JTextField usernameTextField = new JTextField();
        setTextFieldSettings(usernameTextField, 135);

        JLabel passwordLabel = new JLabel(PASSWORD_TEXT_LABEL);
        setLabelSettings(passwordLabel, 190);

        JTextField passwordTextField = new JTextField();
        setTextFieldSettings(passwordTextField, 225);

        JButton loginButton = new JButton(LOGIN_TEXT_BUTTON);
        setButtonSettings(loginButton, 285);
        setLoginFunction(loginButton, usernameTextField, passwordTextField);

        JButton registerButton = new JButton(REGISTER_TEXT_BUTTON);
        registerButton.addActionListener(e -> {
            controller.showRegisterPanel();
        });
        setButtonSettings(registerButton, 345);
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.LOGIN;
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

    private void setButtonSettings(JButton button, int y_scale) {
        button.setBounds(X_SCALE, y_scale, LABEL_WIDTH, LABEL_HEIGHT + 10);
        button.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        button.setBackground(Color.GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        add(button);
    }

    private void setLoginFunction(JButton loginButton, JTextField usernameTextField, JTextField passwordTextField) {
        loginButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            if ((username.isEmpty() || username.isBlank()) || (password.isEmpty() || password.isBlank())) {
                throw new NullPointerException(USERNAME_PASSWORD_CANNOT_EMPTY);
            }

            User user = userManager.login(username, password);
            if (user == null) {
                throw new NullPointerException(USER_DOES_NOT_EXIST);
            }

            // TODO: Add menu
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            controller.showMainPanel();
        });
    }
}
