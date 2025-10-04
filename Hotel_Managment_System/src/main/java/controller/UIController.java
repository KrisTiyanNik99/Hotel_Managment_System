package controller;

import models.enums.UIElement;

public interface UIController {
    void registerComponents(UIElement component);
    void showLoginPanel();
    void showRegisterPanel();
    void showMainPanel(Integer userId);
    void showAdminPanel();
    void createRepositoryInstances();
    void createManagerInstances();
}
