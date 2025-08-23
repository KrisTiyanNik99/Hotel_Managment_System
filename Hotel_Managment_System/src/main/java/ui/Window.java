package ui;

import ui.components.AbstractsUIElement;

public interface Window {
    void registerPanel(String element, AbstractsUIElement panel);
    void showPanel(String panelName);
    void replaceRegisteredPanel(String element, AbstractsUIElement uiComponent);
}
