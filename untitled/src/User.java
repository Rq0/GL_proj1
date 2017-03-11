/**
 * Класс пользователя
 * Created by rq0 on 07.03.2017.
 */
public class User {
    String login;
    String pass;
    Integer id;

    public User(int id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }
}
