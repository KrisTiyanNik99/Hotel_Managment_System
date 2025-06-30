package controller;

import ui.components.AbstractsUIElement;

public interface UIController {
    void registerComponents(AbstractsUIElement component);
    void showLoginPanel();
    void showRegisterPanel();
    void showMainPanel();
}
