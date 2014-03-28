package db;

/**
 * Created by uzzz on 28.03.14.
 */

public class UserDataSet {
    private long userId;
    private String username;
    private String password;

    public UserDataSet(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDataSet(long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}