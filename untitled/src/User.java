/**
 * Класс пользователя
 * Created by rq0 on 07.03.2017.
 */
public class User {
    String login, pass, salt;
    Integer id;

    public User(int id, String login, String pass, String salt) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.salt = salt;
    }
}
