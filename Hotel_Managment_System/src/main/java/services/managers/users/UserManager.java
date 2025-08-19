package services.managers.users;

import models.user.User;

public interface UserManager {
    User register(String username, String password) throws Exception;
    User login(String username, String password);
}
