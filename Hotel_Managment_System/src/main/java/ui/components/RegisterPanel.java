package ui.components;

import models.enums.UIElement;
import models.user.User;
import services.managers.users.UserManager;

import javax.swing.*;
import java.awt.*;

import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public class RegisterPanel extends AbstractsUIElement implements UIComponent {
    private static final int X_SCALE = 240;

    private final UserManager userManager;

    public RegisterPanel(UserManager userManager) {
        this.userManager = userManager;

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

        JButton registerButton = new JButton(LOGIN_TEXT_BUTTON);
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

            try {
                User user = userManager.register(username, password);
                // Add menu panels -------------------------------------------------------------------------------------
            } catch (Exception ex) {
                // Add pane --------------------------------------------------------------------------------------------
                System.out.println(ex.getMessage());
            }
        });
    }
}
