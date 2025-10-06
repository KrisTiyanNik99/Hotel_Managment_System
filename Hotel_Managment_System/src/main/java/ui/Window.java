package ui;

import ui.components.AbstractsUIElement;

/**
 * Defines the main window navigation and layout operations.
 * Enables panel registration, swapping, and display control.
 */
public interface Window {
    /**
     * Registers a new panel with a unique identifier.
     *
     * @param element panel name identifier
     * @param panel   panel instance
     */
    void registerPanel(String element, AbstractsUIElement panel);

    /**
     * Displays a registered panel by name.
     *
     * @param panelName panel identifier
     */
    void showPanel(String panelName);

    /**
     * Replaces an already registered panel with a new instance.
     *
     * @param element panel identifier
     * @param panel   new panel instance
     */
    void replacePanel(String element, AbstractsUIElement panel);
}
