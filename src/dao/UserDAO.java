package dao;

import services.Main;
import domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

public class UserDAO {
    private static final Logger log = LogManager.getLogger(Main.class.getName());

    void addUser(int id, String login, String pass, String salt) {
        String table = "Users";
        String paramsValues = String.format("%s,'%s', '%s','%s'", id, login, pass, salt);
        new services.DbContext().insert(table, paramsValues);
        log.info("Добавлен пользователь {} в бд", id);
    }

    public User selectUser(int userId) {
        String table = "Users";
        String params = "ID,LOGIN, PASS, SALT  ";
        String filter = "where id = " + userId;
        ResultSet selected = new services.DbContext().select(table, params, filter);

        try {
            selected.getMetaData().getColumnCount();
            selected.next();
            log.info("Выборка пользователя из бд");
            return new domain.User(
                    selected.getInt(1),
                    selected.getString(2),
                    selected.getString(3),
                    selected.getString(4));
        } catch (Exception e) {
            log.error("SelectUser {} error {};", userId, e.getMessage());
            System.exit(-104);
        }
        return null;
    }
}
