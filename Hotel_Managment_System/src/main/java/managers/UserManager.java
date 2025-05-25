package managers;

import models.user.User;
import models.user.UserImpl;
import services.RepoService;

public class UserManager {
    private final RepoService<User> userRepoService;

    public UserManager(RepoService<User> userRepoService) {
        this.userRepoService = userRepoService;
    }

    public User register(String username, String password) {
        // Тук може да сложим хешираща функция на паролата, но засега е добре така!
        int userId = userRepoService.generateNextId();

        userRepoService.createValue(
                new UserImpl(userId,
                        username,
                        password));

        return getUserById(userId);
    }

    public User login(String username, String password) {
        // При нормални условия би било по- добре да върнем Optional<T>
        return userRepoService.findAll()
                .stream()
                .filter(e -> username.equals(e.getUsername()))
                .filter(e -> password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);
    }

    public User getUserById(int userId) {
        return userRepoService.findById(userId);
    }
}
