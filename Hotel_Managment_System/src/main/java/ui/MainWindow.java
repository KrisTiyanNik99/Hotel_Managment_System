package ui;

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
    private final Map<String, JPanel> registeredPanels;

    public MainWindow() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        registeredPanels = new HashMap<>();

        initCommonElements();
    }

    private void initCommonElements() {
        setTitle("Hotel Manager System");
        setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPanel);
    }

    @Override
    public void registerPanel(String element, JPanel panel) {
        if (registeredPanels.containsKey(element)) {
            showPanel(element);
            return;
        }

        contentPanel.add(panel, element);
        registeredPanels.put(element, panel);
    }

    @Override
    public void showPanel(String panelName) {
        if (!registeredPanels.containsKey(panelName)) {
            JOptionPane.showMessageDialog(
                    this,
                    PANEL_NOT_FOUND_MESSAGE,
                    NOT_FOUND_SUCH_PANEL,
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        cardLayout.show(contentPanel, panelName);
    }

    @Override
    public void repaintPanel(String panelName, JPanel newPanel) {
         if (!registeredPanels.containsKey(panelName)) {
             JOptionPane.showMessageDialog(
                     this,
                     PANEL_NOT_FOUND_MESSAGE,
                     NOT_FOUND_SUCH_PANEL,
                     JOptionPane.ERROR_MESSAGE
             );

             return;
         }

         JPanel oldPanel = registeredPanels.get(panelName);
         contentPanel.remove(oldPanel);

         contentPanel.add(newPanel, panelName);
         registeredPanels.put(panelName, newPanel);
         contentPanel.revalidate();
         contentPanel.repaint();
    }
}
