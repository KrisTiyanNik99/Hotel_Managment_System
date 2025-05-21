package services;

import config.Configurations;
import models.user.User;
import models.user.UserImpl;

import java.util.Map;

public class UserRepoService extends RepoService<User> {
    public UserRepoService(String repositoryFileName) {
        super(repositoryFileName);
    }

    @Override
    public User findById(int id) {
        User copy = super.findById(id);

        return copy == null ? null : new UserImpl(copy.getUserId(),
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
    public void updateValue(User user) {
        if (user == null || !existsById(user.getUserId())) {
            System.out.println("Such user is not found!");
            return;
        }

        getEntityMap().put(user.getUserId(), user);
        persistToFile();
        System.out.println("Successfully change user in file!");
    }

    @Override
    public void createValue(User user) {
        if (user == null || existsById(user.getUserId())) {
            System.out.println("User cannot be null or already existed!");
            return;
        }

        getEntityMap().put(user.getUserId(), user);
        persistToFile();
        setNewId(user.getUserId());
        System.out.println("Successfully add new user in file!");
    }

    @Override
    protected void mapDataFromFileLine(Map<Integer, User> entityMap, String[] sourceObjData) {
        User user = getObjectFromData(sourceObjData);
        entityMap.put(user.getUserId(), user);
    }

    @Override
    protected String initializeFileName() {
        return Configurations.USER_FILE_NAME;
    }
}
