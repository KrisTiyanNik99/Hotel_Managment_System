package ui.components;

import models.enums.UIElement;

import javax.swing.*;

public abstract class AbstractsUIElement extends JPanel implements UIComponent {
    private final UIElement elementType = getElementType();

    @Override
    public String getType() {
        return elementType.getTypeAsString();
    }

    protected abstract UIElement getElementType();
}
