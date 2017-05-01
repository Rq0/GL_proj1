package main.java.dao;

import main.java.domains.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.java.services.DbContext;

import java.sql.ResultSet;

public class UserDAO {
    private static final Logger log = LogManager.getLogger();
    private String table = "Users";
    void addUser(int id, String login, String pass, String salt) {

        String paramsValues = String.format("%s,'%s', '%s','%s'", id, login, pass, salt);
        new DbContext().insert(table, paramsValues);
        log.info("Добавлен пользователь {} в бд", id);
    }

    public User selectUser(int userId) {

        String params = "ID,LOGIN, PASS, SALT  ";
        String filter = "where id = " + userId;
        ResultSet selected = new DbContext().select(table, params, filter);

        try {
            selected.getMetaData().getColumnCount();
            selected.next();
            log.info("Выборка пользователя из бд");
            return new User(
                    selected.getInt(1),
                    selected.getString(2),
                    selected.getString(3),
                    selected.getString(4));
        } catch (Exception e) {
            log.error("SelectUser {} error;", userId);
            System.exit(-104);
        }
        return null;
    }
    public User selectUserByLogin(String login) {

        String params = "ID,LOGIN, PASS, SALT  ";

        String filter = String.format("where login = '%s' ",login);
        ResultSet selected = new DbContext().select(table, params, filter);

        try {
            selected.getMetaData().getColumnCount();
            selected.next();
            log.info("Выборка пользователя из бд");
            return new User(
                    selected.getInt(1),
                    selected.getString(2),
                    selected.getString(3),
                    selected.getString(4));
        } catch (Exception e) {
            log.error("SelectUserByLogin error ;");
        }
        return null;
    }
    public Integer getLastUserId(){

        ResultSet selected = new DbContext().select(table, "Max(ID)", "");
        try {
            selected.next();
            selected.getInt(1);
            return selected.getInt(1)+1;
        } catch (Exception e) {
            log.error("GetLastUserId error; {}");
        }
        return null;
    }
}
