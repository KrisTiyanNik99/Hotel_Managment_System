package services.managers.users;

import models.user.User;
import models.user.UserImpl;
import services.repos.RepoService;

public class UserManagerImpl implements UserManager {
    private final RepoService<User> userRepoService;

    public UserManagerImpl(RepoService<User> userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    public User register(String username, String password) {
        // Тук може да сложим хешираща функция на паролата, но засега е добре така!
        int userId = userRepoService.generateNextId();

        userRepoService.createValue(
                new UserImpl(userId,
                        username,
                        password));

        return userRepoService.findById(userId);
    }

    @Override
    public User login(String username, String password) {
        // При нормални условия би било по- добре да върнем Optional<T>
        return userRepoService.findAll()
                .stream()
                .filter(e -> username.equals(e.getUsername()))
                .filter(e -> password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);
    }
}
