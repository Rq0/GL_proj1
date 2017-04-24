package domain;

public class User {
    public final String login;
    public final String pass;
    public final String salt;
    public final int id;

    public User(int id, String login, String pass, String salt) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.salt = salt;
    }
}