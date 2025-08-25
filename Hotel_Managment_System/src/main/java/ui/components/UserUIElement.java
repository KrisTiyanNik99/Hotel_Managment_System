package ui.components;

import controller.UIController;

import javax.swing.*;
import java.awt.*;

import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public abstract class UserUIElement extends AbstractsUIElement {
    protected UserUIElement(UIController controller) {
        super(controller);

        setBackground(Color.DARK_GRAY);
    }

    protected void setMenuLabelSettings(JLabel label, int x_scale, int y_scale) {
        label.setBounds(x_scale, y_scale, LABEL_WIDTH + LABEL_WIDTH, LABEL_HEIGHT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        label.setOpaque(false);
        add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public abstract void setUserById(Integer userId);
}
