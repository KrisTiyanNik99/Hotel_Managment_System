package ui.components;

import controller.UIController;
import models.enums.UIElement;

import javax.swing.*;
import java.awt.*;

import static config.UIStyle.*;
import static config.UIStyle.FONT_SIZE;

public abstract class AbstractsUIElement extends JPanel implements UIComponent {
    protected static final int X_SCALE = 240;

    private final UIElement elementType = getElementType();

    protected final UIController controller;
    protected final Window parentWindow;

    protected AbstractsUIElement(UIController controller) {
        this.controller = controller;

        setLayout(null);
        setBackground(Color.CYAN);
        parentWindow = SwingUtilities.getWindowAncestor(this);
    }

    @Override
    public String getType() {
        return elementType.getTypeAsString();
    }

    protected void setLabelSettings(JLabel label, int y_scale) {
        label.setBounds(X_SCALE, y_scale, LABEL_WIDTH, LABEL_HEIGHT);
        label.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        label.setOpaque(false);
        add(label);
    }

    protected void setTextFieldSettings(JTextField textField, int y_scale) {
        textField.setBounds(X_SCALE, y_scale, LABEL_WIDTH, LABEL_HEIGHT);
        textField.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.ORANGE);
        add(textField);
    }

    protected void setButtonSettings(JButton button, int y_scale) {
        button.setBounds(X_SCALE, y_scale, LABEL_WIDTH, LABEL_HEIGHT + 10);
        button.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        button.setBackground(Color.GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        add(button);
    }

    protected void setMenuLabelSettings(JLabel label, int x_scale, int y_scale) {
        label.setBounds(x_scale, y_scale, LABEL_WIDTH + LABEL_WIDTH, LABEL_HEIGHT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        label.setOpaque(false);
        add(label);
    }

    protected void setMenuButtonSettings(JButton button, int x_scale, int y_scale) {
        button.setBounds(x_scale,y_scale, LABEL_WIDTH, LABEL_HEIGHT);
        button.setBackground(Color.CYAN);
        button.setForeground(Color.BLACK);
        button.setFont(new Font(ARIEL_STYLE, Font.BOLD, FONT_SIZE));
        button.setFocusPainted(false);
        add(button);
    }

    protected abstract UIElement getElementType();
}
