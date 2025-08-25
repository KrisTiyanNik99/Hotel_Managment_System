package ui;

import ui.components.AbstractsUIElement;

public interface Window {
    void registerPanel(String element, AbstractsUIElement panel);
    void showPanel(String panelName);
    void replacePanel(String element, AbstractsUIElement panel);
}
