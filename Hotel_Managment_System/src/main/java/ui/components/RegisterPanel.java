package ui.components;

import models.enums.UIElement;
import services.managers.users.UserManager;

public class RegisterPanel implements UIComponent {
    private final UIElement uiElement = UIElement.REGISTER;

    private final UserManager userManager;

    public RegisterPanel(UserManager userManager) {
        this.userManager = userManager;

        initComponents();
    }

    private void initComponents() {
    }

    @Override
    public String getType() {
        return uiElement.getTypeAsString();
    }
}
