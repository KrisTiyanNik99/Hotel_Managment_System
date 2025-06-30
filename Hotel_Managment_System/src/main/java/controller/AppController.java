package controller;

import models.enums.UIElement;
import ui.Window;
import ui.components.AbstractsUIElement;
import ui.components.LoginPanel;
import ui.components.RegisterPanel;
import ui.components.UIComponent;

public class AppController implements UIController {
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    //private

    private final Window mainWindow;
    private final ManagerController managerController;

    public AppController(Window mainWindow, ManagerController managerController) {
        this.mainWindow = mainWindow;
        this.managerController = managerController;
        
        showLoginPanel();
    }

    @Override
    public void registerComponents(AbstractsUIElement component) {
        mainWindow.registerPanel(component.getType(), component);
        if (compareUIComponentTypes(component, UIElement.LOGIN)){
            loginPanel = (LoginPanel) component;
        } else if (compareUIComponentTypes(component, UIElement.REGISTER)) {
            //registerPanel = (RegisterPanel) component;
        }
    }

    @Override
    public void showLoginPanel() {
        mainWindow.showPanel(UIElement.LOGIN.getTypeAsString());
    }

    @Override
    public void showRegisterPanel() {
        mainWindow.showPanel(UIElement.REGISTER.getTypeAsString());
    }

    @Override
    public void showMainPanel() {
        mainWindow.showPanel(UIElement.MENU.getTypeAsString());
    }

    private boolean compareUIComponentTypes(UIComponent component, UIElement uiElement) {
        return component.getType().equalsIgnoreCase(uiElement.getTypeAsString());
    }
}
