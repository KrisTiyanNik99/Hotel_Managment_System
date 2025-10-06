package services.managers.users;

import models.user.User;

/**
 * Defines user operations for registration, authentication, and data retrieval.
 * Provides core user-related business logic.
 */
public interface UserManager {
    /**
     * Registers a new user with provided credentials.
     *
     * @param username desired username
     * @param password user password
     * @return the created {@link User} instance
     * @throws Exception if registration fails (e.g., duplicate username or repository issue)
     */
    User register(String username, String password) throws Exception;

    /**
     * Authenticates a user and returns their unique ID if credentials are valid.
     *
     * @param username username
     * @param password password
     * @return user ID if credentials are correct; otherwise null
     */
    Integer login(String username, String password);

    /**
     * Checks if a user with the provided credentials exists.
     *
     * @param username username
     * @param password password
     * @return true if user exists, false otherwise
     */
    boolean isUserExist(String username, String password);

    /**
     * Retrieves username by user ID.
     *
     * @param id user identifier
     * @return username string
     */
    String getUsernameByUserId(Integer id);
}
