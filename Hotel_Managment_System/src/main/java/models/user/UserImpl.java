package models.user;

public class UserImpl implements User {
    private final int id;
    private final String username;
    private final String password;

    public UserImpl(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public int getId() {
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
        return String.format("No:%d; Name:%s; Password:%s;%n",
                id,
                username,
                password);
    }
}
