package models.enums;

public enum UIElement {
    LOGIN("LOGIN"),
    REGISTER("REGISTER"),
    MENU("MENU");

    private final String type;
    UIElement(String type) {
        this.type = type;
    }

    public String getTypeAsString() {
        return type;
    }
}
