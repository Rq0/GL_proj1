/**
 * Created by rq0 on 07.03.2017.
 */
public class User {
    String login;
    String pass;
    Integer ID;

    public User(int ID, String login, String pass) {
        this.ID = ID;
        this.login = login;
        this.pass = pass;
    }

    public User() {
        this.ID = 1024;
        this.login = "root";
        this.pass = "toor";
    }
}
