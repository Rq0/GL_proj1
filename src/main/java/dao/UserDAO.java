package main.java.dao;

import main.java.domains.User;
import main.java.services.DbContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private static final Logger log = LogManager.getLogger();
    private String table = "Users";

    public User selectUserById(int userId) {

        DbContext dbContext = new DbContext();
        dbContext.connect();
        String sqlSelectQuery = "Select ID,LOGIN, PASS, SALT  From Users where id = ? ";

        ResultSet selected;
        try (PreparedStatement preparedStatement = dbContext.getConnection().prepareStatement(sqlSelectQuery)) {
            preparedStatement.setInt(1, userId);
            selected = preparedStatement.executeQuery();
            if (selected.next()) { //проверяем вернулся ли хоть 1 пользователь с таким id
                log.info("Выборка пользователя из бд");
                return new User(
                        selected.getInt(1),
                        selected.getString(2),
                        selected.getString(3),
                        selected.getString(4));
            }
        } catch (Exception e) {
            log.error("SelectUser {} error;", userId);
        }
        return null;
    }

    public User selectUserByLogin(String login) {

        DbContext dbContext = new DbContext();
        dbContext.connect();
        String sqlSelectQuery = "Select ID,LOGIN, PASS, SALT  From Users where login = ? ";

        ResultSet selected;
        try (PreparedStatement preparedStatement = dbContext.getConnection().prepareStatement(sqlSelectQuery)) {
            preparedStatement.setString(1, login);
            selected = preparedStatement.executeQuery();
            if (selected.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                log.info("Выборка пользователя из бд");
                return new User(
                        selected.getInt(1),
                        selected.getString(2),
                        selected.getString(3),
                        selected.getString(4));
            }
        } catch (Exception e) {
            log.error("SelectUserByLogin {} error ;", login);
        }
        return null;
    }
}
