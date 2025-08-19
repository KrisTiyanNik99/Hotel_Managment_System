package ui;

import javax.swing.*;
import java.awt.*;

import static config.UIStyle.MAIN_WINDOW_HEIGHT;
import static config.UIStyle.MAIN_WINDOW_WIDTH;

public class MainWindow extends JFrame implements Window {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MainWindow() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

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
        contentPanel.add(panel, element);
    }

    @Override
    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
}
