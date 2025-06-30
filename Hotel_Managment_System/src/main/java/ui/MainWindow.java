package ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements Window{
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MainWindow() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        initCommonElements();
    }

    private void initCommonElements() {
        setTitle("Hotel Manager System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
