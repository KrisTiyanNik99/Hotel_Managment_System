package services.managers.users;

import models.user.User;

public interface UserManager {
    User register(String username, String password) throws Exception;
    Integer login(String username, String password);
    boolean isUserExist(String username, String password);
}
