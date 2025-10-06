package services.repos;

import config.Configurations;
import models.user.User;
import models.user.UserImpl;

/**
 * Repository service responsible for managing {@link User} entities.
 * Handles file-based serialization and deserialization of user records.
 * Provides data reconstruction logic and ensures that returned objects
 * are detached copies to prevent external mutation.
 */
public class UserRepoService extends RepoService<User> {

    /**
     * Constructs a {@code UserRepoService} using the specified file name.
     *
     * @param repositoryFileName the repository file name
     */
    public UserRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    /**
     * Returns a detached copy of the {@link User} entity to ensure immutability
     * of stored references.
     *
     * @param id the user ID
     * @return a copied {@link UserImpl} instance, or {@code null} if not found
     */
    @Override
    public User findById(Integer id) {
        User copy = super.findById(id);
        return copy == null ? null : new UserImpl(
                copy.getId(),
                copy.getUsername(),
                copy.getPassword()
        );
    }

    /**
     * Converts serialized data into a {@link UserImpl} instance.
     *
     * @param data the split key-value array
     * @return a new {@link UserImpl} object
     */
    @Override
    public User getObjectFromData(String[] data) {
        Integer id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
        String username = data[1].split(REGEX_EXPRESSION)[VALUE_POSITION];
        String password = data[2].split(REGEX_EXPRESSION)[VALUE_POSITION];

        setNewId(id);
        return new UserImpl(id, username, password);
    }

    @Override
    protected String typeName() {
        return "User";
    }

    @Override
    protected String initializeFileName() {
        return Configurations.USER_FILE_NAME;
    }
}
