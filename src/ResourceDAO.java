import java.sql.ResultSet;

class ResourceDAO {
    void addResource(String path, User user, Role role, DbContext dbContext) {
        String table = "Resources";
        String params = "path varchar(255), userId int, role varchar(255)";

        String paramsValues = String.format("'%s', %s,'%s'", path, user.id, role);
        dbContext.createTable(table, params);
        dbContext.insert(table, paramsValues);
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
            System.out.println("SelectResourceError");
        }
        return null;
    }
}
