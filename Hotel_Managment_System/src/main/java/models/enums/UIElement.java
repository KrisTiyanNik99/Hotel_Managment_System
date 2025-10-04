package models.enums;

public enum UIElement {
    LOGIN("LOGIN"),
    REGISTER("REGISTER"),
    MENU("MENU"),
    ADMIN("ADMIN");

    private final String type;
    UIElement(String type) {
        this.type = type;
    }

    public String getTypeAsString() {
        return type;
    }
}
