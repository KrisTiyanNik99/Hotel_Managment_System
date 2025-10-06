package services.managers.users;

import models.user.User;

import java.util.List;

public interface AdminUserManager extends UserManager{
    void deleteUser(User user);
    List<User> getAll();
}
