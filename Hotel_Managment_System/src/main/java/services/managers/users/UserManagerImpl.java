package services.managers.users;

import models.user.User;
import models.user.UserImpl;
import services.repos.RepoService;

import static config.ConstantMessages.USER_ALREADY_EXIST;

public class UserManagerImpl implements UserManager {
    private final RepoService<User> userRepoService;

    public UserManagerImpl(RepoService<User> userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    public User register(String username, String password) throws Exception {
        // Тук може да сложим хешираща функция на паролата, но засега е добре така!
        int userId = userRepoService.generateNextId();
        User user = login(username, password);

        if (user != null) {
            throw new Exception(USER_ALREADY_EXIST);
        }

        userRepoService.createValue(
                new UserImpl(userId,
                        username,
                        password));

        return userRepoService.findById(userId);
    }

    @Override
    public User login(String username, String password) {
        return userRepoService.findAll()
                .stream()
                .filter(e -> username.equals(e.getUsername()))
                .filter(e -> password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);
    }
}
