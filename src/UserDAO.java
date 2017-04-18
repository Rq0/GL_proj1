import java.sql.ResultSet;

class UserDAO {
    void AddUser(int id, String login, String pass, String salt, DbContext dbContext) {
        String table = "Users";
        String params = "id int, login varchar(255) primary key, pass varchar(255), salt varchar(255)";

        String paramsValues = String.format("%s,'%s', '%s','%s'", id, login, pass, salt);
        dbContext.CreateTable(table, params);
        dbContext.Insert(table, paramsValues);
    }

    User SelectUser(int userId, String filter2, DbContext dbContext) {
        String table = "Users";
        String params = "ID,LOGIN, PASS, SALT  ";
        String filter = "where id = " + userId + filter2;
        ResultSet selected = dbContext.Select(table, params, filter);

        try {
            selected.getMetaData().getColumnCount();
            while (selected.next()) {
                return new User(
                        selected.getInt(1),
                        selected.getString(2),
                        selected.getString(3),
                        selected.getString(4));
            }
        } catch (Exception e) {
            System.out.println("SelectUserError");
            System.exit(-104);
        }
        return null;
    }
}
