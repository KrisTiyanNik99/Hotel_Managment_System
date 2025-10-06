package models.enums;

/**
 * Represents the current state of a room.
 */
public enum Status {
    AVAILABLE,
    BOOKED;

    /**
     * Parses a string value into a {@link Status} instance.
     * Defaults to {@link #BOOKED} if the provided text is invalid.
     *
     * @param text status text (case-insensitive)
     * @return parsed {@link Status}
     * @throws IllegalArgumentException if text is null
     */
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
