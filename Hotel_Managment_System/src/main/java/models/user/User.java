package models.user;

import func.FileFormatter;

public interface User extends FileFormatter {
    int getUserId();
    String getUsername();
    String getPassword();
}
