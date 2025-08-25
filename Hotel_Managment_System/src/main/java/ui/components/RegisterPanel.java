package ui.components;

import controller.UIController;
import models.enums.UIElement;
import services.managers.users.UserManager;

import javax.swing.*;

import static config.ConstantMessages.*;
import static config.UIStyle.*;

public class RegisterPanel extends AbstractsUIElement {
    private final UserManager userManager;

    public RegisterPanel(UserManager userManager, UIController controller) {
        super(controller);
        this.userManager = userManager;

        initComponents();
    }

    @Override
    public void initComponents() {
        JLabel usernameLabel = new JLabel(USERNAME_TEXT_LABEL);
        setLabelSettings(usernameLabel, 110);

        JTextField usernameTextField = new JTextField();
        setTextFieldSettings(usernameTextField, 145);

        JLabel passwordLabel = new JLabel(PASSWORD_TEXT_LABEL);
        setLabelSettings(passwordLabel, 200);

        JTextField passwordTextField = new JTextField();
        setTextFieldSettings(passwordTextField, 235);

        JButton registerButton = new JButton(REGISTRATION_TEXT_BUTTON);
        setButtonSettings(registerButton, 295);

        JButton toLoginButton = new JButton(TO_LOGIN_TEXT);
        setButtonSettings(toLoginButton, 355);
        toLoginButton.addActionListener(e -> controller.showLoginPanel());

        setRegisterFunction(registerButton, usernameTextField, passwordTextField);
    }

    @Override
    protected UIElement getElementType() {
        return UIElement.REGISTER;
    }

    private void setRegisterFunction(JButton registerButton, JTextField usernameTextField, JTextField passwordTextField) {
        registerButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            if (fieldsValidations(username, password)) return;

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

    private boolean fieldsValidations(String username, String password) {
        if ((username.isEmpty() || username.isBlank()) || (password.isEmpty() || password.isBlank())) {
            JOptionPane.showMessageDialog(
                    this,
                    USERNAME_PASSWORD_CANNOT_EMPTY,
                    EMPTY_FIELDS,
                    JOptionPane.ERROR_MESSAGE);

            return true;
        }

        if (userManager.isUserExist(username, password)) {
            JOptionPane.showMessageDialog(
                    this,
                    USER_ALREADY_EXIST,
                    EXISTED_USER,
                    JOptionPane.ERROR_MESSAGE);

            return true;
        }

        return false;
    }
}
