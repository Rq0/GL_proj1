package dao;

import domain.Resource;
import domain.Role;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AAAService;
import services.DbContext;

import java.sql.ResultSet;

public class ResourceDAO {
    private static final Logger log = LogManager.getLogger();
    private final String table = "Resources";

    void addResource(String path, User user, Role role, DbContext dbContext) {

        String paramsValues = String.format("'%s', %s,'%s'", path, user.id, role);
        dbContext.insert(table, paramsValues);
        log.info("Добавлен ресурс {} для {} в бд", path, user.id);
    }

    public boolean haveAccess(String path, Role role, int userId) {
        ResultSet result = new DbContext().select(table, "*", String.format(
                "where path like '%s' " +
                        "and role like '%s' " +
                        "and userid like %s ", path, role, userId));
        try {
            if (result.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                return true;
            }
        } catch (Exception e) {
            log.fatal("Серьезное че-то с доступом;");
        }
        return false;
    }

    public Resource getResource(String path, int userId) {
        ResultSet result = new DbContext().select(table, "*", String.format(
                "where path like '%s' " +
                        "and userid like %s ", path, userId));
        try {
            if (result.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                return new Resource(
                        result.getInt("ID"),
                        result.getString(2),
                        new AAAService().getUser(result.getInt(1)),
                        Role.valueOf(result.getString(4)));
            }
        } catch (Exception e) {
            log.error("Не прошло определение доступа к ресурсу {}", path);
        }
        return null;
    }

    Resource selectResource(int id) {
        String params = "id,  path, userId, role";
        String filter = "where id = " + id;
        ResultSet selected = new DbContext().select(table, params, filter);
        UserDAO userDAO = new UserDAO();
        try {
            selected.next();
            return new Resource(
                    selected.getInt(1),
                    selected.getString(2),
                    userDAO.selectUser(selected.getInt(3)),
                    Role.valueOf(selected.getString(4)));
        } catch (Exception e) {
            log.error("SelectResource {} error", id);
        }
        return null;
    }
}
