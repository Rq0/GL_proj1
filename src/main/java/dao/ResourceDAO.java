package main.java.dao;

import main.java.domains.Resource;
import main.java.domains.Role;
import main.java.services.AAAService;
import main.java.services.DbContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResourceDAO {
    private static final Logger log = LogManager.getLogger();
    private final String table = "Resources";

    public boolean haveAccess(String path, Role role, int userId) {
        DbContext dbContext = new DbContext();
        dbContext.connect();
        String sqlSelectQuery = "Select * From Resources where path like ? and role like  ? and userid like ?";

        ResultSet selected;
        try (PreparedStatement preparedStatement = dbContext.getConnection().prepareStatement(sqlSelectQuery)) {
            preparedStatement.setString(1, path);
            preparedStatement.setString(2, role.toString());
            preparedStatement.setInt(3, userId);
            selected = preparedStatement.executeQuery();
            if (selected.next()) {
                return true;
            }
        } catch (Exception e) {
            log.fatal("Серьезное че-то с доступом;");
        }
        return false;
    }

    public Resource getResource(String path, int userId) {
        DbContext dbContext = new DbContext();
        dbContext.connect();
        String sqlSelectQuery = "Select * From Resources where path like ? and userid like  ? ";

        ResultSet selected;
        try (PreparedStatement preparedStatement = dbContext.getConnection().prepareStatement(sqlSelectQuery)) {
            preparedStatement.setString(1, path);
            preparedStatement.setInt(2, userId);
            selected = preparedStatement.executeQuery();
            if (selected.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                return new Resource(
                        selected.getInt("ID"),
                        selected.getString(2),
                        new AAAService().getUser(selected.getInt(1)),
                        Role.valueOf(selected.getString(4)));
            }
        } catch (Exception e) {
            log.error("Не прошло определение доступа к ресурсу {}", path);
        }
        return null;
    }
}
