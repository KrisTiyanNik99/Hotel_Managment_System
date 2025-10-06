package models.enums;

/**
 * Defines the available user interface panels that can be displayed in the application.
 */
public enum UIElement {
    LOGIN("LOGIN"),
    REGISTER("REGISTER"),
    MENU("MENU"),
    ADMIN("ADMIN");

    private final String type;

    UIElement(String type) {
        this.type = type;
    }

    /**
     * @return the string representation of this UI element type.
     */
    public String getTypeAsString() {
        return type;
    }
}
