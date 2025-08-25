package ui;

import ui.components.AbstractsUIElement;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static config.ConstantMessages.*;
import static config.UIStyle.MAIN_WINDOW_HEIGHT;
import static config.UIStyle.MAIN_WINDOW_WIDTH;

public class MainWindow extends JFrame implements Window {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final Map<String, AbstractsUIElement> registeredPanels;

    public MainWindow() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        registeredPanels = new HashMap<>();

        initCommonElements();
    }

    private void initCommonElements() {
        setTitle(APPLICATION_TITLE);
        setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPanel);
    }

    // TODO: Add method who return registred panel

    @Override
    public void registerPanel(String element, AbstractsUIElement panel) {
        if (registeredPanels.containsKey(element)) {
            JOptionPane.showMessageDialog(
                    this,
                    EXISTED_PANEL,
                    EXISTED_PANEL_TITLE,
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        contentPanel.add(panel, element);
        registeredPanels.put(element, panel);
    }

    @Override
    public void showPanel(String panelName) {
        if (isPanelExist(panelName)) return;

        cardLayout.show(contentPanel, panelName);
    }

    @Override
    public void replacePanel(String element, AbstractsUIElement uiComponent) {
        if (isPanelExist(element)) return;

        JPanel currentPanel = registeredPanels.get(element);
        contentPanel.remove(currentPanel);

        contentPanel.add(uiComponent, element);
        registeredPanels.put(element, uiComponent);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private boolean isPanelExist(String panelName) {
        if (!registeredPanels.containsKey(panelName)) {
            JOptionPane.showMessageDialog(
                    this,
                    PANEL_NOT_FOUND_MESSAGE,
                    NOT_FOUND_SUCH_PANEL,
                    JOptionPane.ERROR_MESSAGE
            );

            return true;
        }

        return false;
    }
}
