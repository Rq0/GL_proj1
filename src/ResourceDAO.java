import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

class ResourceDAO {
    private static final Logger log = LogManager.getLogger(Main.class.getName());
    private String table = "Resources";

    void addResource(String path, User user, Role role, DbContext dbContext) {

        String paramsValues = String.format("'%s', %s,'%s'", path, user.id, role);
        dbContext.insert(table, paramsValues);
        log.info("Добавлен ресурс {} для {} в бд", path, user.id);
    }

    Resource selectResource(int id, DbContext dbContext) {
        String table = "Resources";
        String params = "id,  path, userId, role";
        String filter = "where id = " + id;
        ResultSet selected = dbContext.select(table, params, filter);
        UserDAO userDAO = new UserDAO();
        try {
            selected.last();
            return new Resource(
                    selected.getInt(1),
                    selected.getString(2),
                    userDAO.selectUser(selected.getInt(3), dbContext),
                    Role.valueOf(selected.getString(4)));
        } catch (Exception e) {
            log.error("SelectResource {} error; {}", id, e.getMessage());
        }
        return null;
    }
}
