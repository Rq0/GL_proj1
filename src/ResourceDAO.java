import java.sql.ResultSet;

class ResourceDAO {
    void AddResource(int id, String path, User user, Role role, DbContext dbContext) {
        String table = "Resources";
        String params = "id int,  path varchar(255), userId int, role varchar(255)";

        String paramsValues = String.format("%s,'%s', '%s','%s'", id, path, user.id, role);
        dbContext.CreateTable(table, params);
        dbContext.Insert(table, paramsValues);
    }
    Resource SelectResource(int resourceId,DbContext dbContext){
        String table = "Resources";
        String params = "id,  path, userId, role";
        String  filter = "where id = "+ resourceId;
        ResultSet selected = dbContext.Select(table,params,filter);
        UserDAO userDAO = new UserDAO();
        try {
            return new Resource(
                    selected.getInt(1),
                    selected.getString(2),
                    userDAO.SelectUser( selected.getInt(3),"", dbContext),
                    Role.valueOf(selected.getString(4)));
        }
        catch (Exception e){
            System.out.println("SelectResourceError");
        }
        return null;
    }
}
