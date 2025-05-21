package models.enums;

public enum Status {
    AVAILABLE,
    BOOKED;

    public static Status parseStatus(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Status text cannot be null");
        }

        try {
            return Status.valueOf(text.trim().toUpperCase());
        } catch (IllegalArgumentException ignore) {
            return BOOKED;
        }
    }
}
