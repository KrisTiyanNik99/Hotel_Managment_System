package services.managers.users;

import models.user.User;
import models.user.UserImpl;
import services.repos.RepoService;

import java.util.Objects;

public class UserManagerImpl implements UserManager {
    private final RepoService<User> userRepoService;

    public UserManagerImpl(RepoService<User> userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    public User register(String username, String password) {
        // Тук може да сложим хешираща функция на паролата, но засега е добре така!
        int userId = userRepoService.generateNextId();

        User currentUser = new UserImpl(userId,
                username,
                password);
        System.out.println(userId);
        userRepoService.createValue(currentUser);

        return userRepoService.findById(userId);
    }

    @Override
    public Integer login(String username, String password) {
        User user = userRepoService.findAll()
                .stream()
                .filter(e -> username.equals(e.getUsername()))
                .filter(e -> password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);

        return Objects.requireNonNull(user).getId();
    }

    @Override
    public boolean isUserExist(String username, String password) {
        User user = userRepoService.findAll()
                .stream()
                .filter(e -> username.equals(e.getUsername()))
                .filter(e -> password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);

        return user != null;
    }

    @Override
    public String getUsernameByUserId(Integer id) {
        return userRepoService.findById(id).getUsername();
    }
}
