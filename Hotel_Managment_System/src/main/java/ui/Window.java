package ui;

import javax.swing.*;

public interface Window {
    void registerPanel(String element, JPanel panel);
    void showPanel(String panelName);
}
