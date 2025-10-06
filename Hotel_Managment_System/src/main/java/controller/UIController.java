package controller;

import models.enums.UIElement;

/**
 * Defines a generic user interface controller contract for managing UI components
 * and transitions between different application views.
 * Implementations of this interface handle the registration of UI elements,
 * view navigation, and initialization of repository and manager instances.
 */
public interface UIController {

    /**
     * Registers a UI component (panel, dialog, etc.) to the main application window.
     *
     * @param component the UI component type to register
     */
    void registerComponents(UIElement component);

    /**
     * Displays the login panel.
     */
    void showLoginPanel();

    /**
     * Displays the registration panel.
     */
    void showRegisterPanel();

    /**
     * Displays the main user panel for the given user.
     * @param userId the ID of the user whose data should be loaded
     */
    void showMainPanel(Integer userId);

    /**
     * Displays the admin panel view.
     */
    void showAdminPanel();

    /**
     * Creates and initializes all repository instances.
     * Repositories are responsible for handling persistent data access and storage.
     */
    void createRepositoryInstances();

    /**
     * Creates and initializes all manager instances.
     * Managers act as business logic layers built on top of repository services.
     */
    void createManagerInstances();
}
