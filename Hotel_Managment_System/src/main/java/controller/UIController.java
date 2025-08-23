package controller;

import models.enums.UIElement;
import ui.components.AbstractsUIElement;

public interface UIController {
    void registerComponents(UIElement component);
    void showLoginPanel();
    void showRegisterPanel();
    void showMainPanel(AbstractsUIElement userJPanel);
    void createRepositoryInstances();
    void createManagerInstances();
}
