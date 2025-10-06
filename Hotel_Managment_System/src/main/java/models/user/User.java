package models.user;

import func.Identifiable;

/**
 * Represents a user entity with authentication credentials.
 */
public interface User extends Identifiable {
    String getUsername();
    String getPassword();
}
