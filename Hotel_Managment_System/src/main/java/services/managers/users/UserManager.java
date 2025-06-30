package services.managers.users;

import models.user.User;

public interface UserManager {
    User register(String username, String password);
    User login(String username, String password);
}
