package utils;

import model.User;

public class Session {
    private static Session instance;
    private User currentUser;

    private Session() {}

    public static synchronized Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public void setUser(User u) { this.currentUser = u; }
    public User getUser() { return currentUser; }
    public void clear() { this.currentUser = null; }
}
