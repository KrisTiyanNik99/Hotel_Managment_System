package services.managers.users;

import models.user.User;
import java.util.List;

/**
 * Extends {@link UserManager} with administrative-level operations.
 * Allows access to all user records and deletion of users.
 */
public interface AdminUserManager extends UserManager {
    /**
     * Deletes a user entry from the system.
     *
     * @param user user instance to delete
     */
    void deleteUser(User user);

    /**
     * Retrieves all user records.
     *
     * @return list of all users
     */
    List<User> getAll();
}
