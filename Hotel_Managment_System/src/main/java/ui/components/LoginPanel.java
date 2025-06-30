package ui.components;

import controller.UIController;
import models.enums.UIElement;
import services.managers.users.UserManager;

public class LoginPanel extends AbstractsUIElement implements UIComponent {
    private final UserManager userManager;
    private final UIController controller;

    public LoginPanel(UserManager userManager, UIController controller) {
        this.userManager = userManager;
        this.controller = controller;

        initComponents();
    }

    private void initComponents() {

    }

    @Override
    protected UIElement getElementType() {
        return UIElement.LOGIN;
    }
}
