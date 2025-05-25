package services;

import config.Configurations;
import models.user.User;
import models.user.UserImpl;

public class UserRepoService extends RepoService<User> {
    public UserRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    @Override
    public User findById(int id) {
        User copy = super.findById(id);

        return copy == null ? null : new UserImpl(copy.getId(),
                copy.getUsername(),
                copy.getPassword());
    }

    @Override
    public User getObjectFromData(String[] data) {
        int id = Integer.parseInt(data[0].split(REGEX_EXPRESSION)[VALUE_POSITION]);
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
