package services.managers.users;

import models.user.User;
import models.user.UserImpl;
import services.repos.RepoService;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link AdminUserManager}.
 * Manages user registration, authentication, and administrative operations.
 */
public record UserManagerImpl(RepoService<User> userRepoService) implements AdminUserManager {

    @Override
    public User register(String username, String password) {
        // A password hashing mechanism can be added later (e.g., SHA-256 or BCrypt)
        int userId = userRepoService.generateNextId();

        User currentUser = new UserImpl(userId, username, password);
        userRepoService.createValue(currentUser);

        return userRepoService.findById(userId);
    }

    @Override
    public void deleteUser(User user) {
        userRepoService.deleteById(user.getId());
    }

    @Override
    public List<User> getAll() {
        return userRepoService.findAll();
    }

    @Override
    public Integer login(String username, String password) {
        User user = userRepoService.findAll()
                .stream()
                .filter(e -> username.equals(e.getUsername()))
                .filter(e -> password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);

        // Note: A NullPointerException will occur if credentials are invalid.
        // In production, better handle this via Optional or custom exception.
        return Objects.requireNonNull(user).getId();
    }

    @Override
    public boolean isUserExist(String username, String password) {
        return userRepoService.findAll()
                .stream()
                .anyMatch(e -> username.equals(e.getUsername()) && password.equals(e.getPassword()));
    }

    @Override
    public String getUsernameByUserId(Integer id) {
        return userRepoService.findById(id).getUsername();
    }
}
