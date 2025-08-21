package controller;

import models.enums.UIElement;
import ui.Window;
import ui.components.AbstractsUIElement;

import javax.swing.*;

public class AppController implements UIController {
//    private LoginPanel loginPanel;
//    private RegisterPanel registerPanel;

    private final Window mainWindow;

    public AppController(Window mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void registerComponents(AbstractsUIElement component) {
//        if (compareUIComponentTypes(component, UIElement.LOGIN)){
//            loginPanel = (LoginPanel) component;
//        } else if (compareUIComponentTypes(component, UIElement.REGISTER)) {
//            //registerPanel = (RegisterPanel) component;
//        }
        mainWindow.registerPanel(component.getType(), component);
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
    public void showMainPanel(JPanel jPanel) {
        mainWindow.repaintPanel(UIElement.MENU.getTypeAsString(), jPanel);
    }

//    private boolean compareUIComponentTypes(UIComponent component, UIElement uiElement) {
//        return component.getType().equalsIgnoreCase(uiElement.getTypeAsString());
//    }
}
