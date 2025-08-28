package models.user;

public class UserImpl implements User {
    private final Integer id;
    private final String username;
    private final String password;

    public UserImpl(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
       return String.format("This is %s user", username);
    }

    @Override
    public String textFormat() {
        return String.format("No:%d; Name:%s; Password:%s;%n",
                id,
                username,
                password);
    }
}
