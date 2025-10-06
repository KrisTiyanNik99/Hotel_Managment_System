package ui.components.auth;

import controller.UIController;
import models.enums.UIElement;
import services.managers.users.UserManager;
import ui.components.AbstractsUIElement;
import ui.components.UIComponent;

import javax.swing.*;

import static config.ConstantMessages.USERNAME_PASSWORD_CANNOT_EMPTY;
import static config.ConstantMessages.USER_DOES_NOT_EXIST;
import static config.UIStyle.*;

/**
 * UI component for handling user login actions.
 * Supports admin redirection and user authentication.
 */
public class LoginPanel extends AbstractsUIElement implements UIComponent {
    private final UserManager userManager;

    /**
     * Constructs the login panel.
     *
     * @param userManager user management service
     * @param controller  UI navigation controller
     */
    public LoginPanel(UserManager userManager, UIController controller) {
        super(controller);
        this.userManager = userManager;
        initComponents();
    }

    /** Initializes all UI components for login. */
    @Override
    public void initComponents() {
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
        registerButton.addActionListener(e -> controller.showRegisterPanel());
        setButtonSettings(registerButton, 345);
    }

    /** {@inheritDoc} */
    @Override
    protected UIElement getElementType() {
        return UIElement.LOGIN;
    }

    /**
     * Sets the login button behavior.
     * Handles admin login and regular user authentication.
     */
    private void setLoginFunction(JButton loginButton, JTextField usernameTextField, JTextField passwordTextField) {
        loginButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            if ((username.isEmpty() || username.isBlank()) || (password.isEmpty() || password.isBlank())) {
                throw new NullPointerException(USERNAME_PASSWORD_CANNOT_EMPTY);
            }

            // Temporary hardcoded admin credentials
            if (username.equals("Dragan") && password.equals("admin123")) {
                controller.showAdminPanel();
                return;
            }

            Integer userId = userManager.login(username, password);
            if (userId == null) {
                throw new NullPointerException(USER_DOES_NOT_EXIST);
            }

            controller.showMainPanel(userId);
        });
    }
}
