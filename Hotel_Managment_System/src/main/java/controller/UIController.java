package controller;

import ui.components.AbstractsUIElement;

import javax.swing.*;

public interface UIController {
    void registerComponents(AbstractsUIElement component);
    void showLoginPanel();
    void showRegisterPanel();
    void showMainPanel(JPanel jPanel);
}
