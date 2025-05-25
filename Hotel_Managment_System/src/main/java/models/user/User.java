package models.user;

import func.Identifiable;

public interface User extends Identifiable {
    String getUsername();
    String getPassword();
}
