import java.sql.ResultSet;

class UserDAO {
    void addUser(int id, String login, String pass, String salt, DbContext dbContext) {
        String table = "Users";
        String paramsValues = String.format("%s,'%s', '%s','%s'", id, login, pass, salt);
        dbContext.insert(table, paramsValues);
    }

    User selectUser(int userId, DbContext dbContext) {
        String table = "Users";
        String params = "ID,LOGIN, PASS, SALT  ";
        String filter = "where id = " + userId;
        ResultSet selected = dbContext.select(table, params, filter);

        try {
            selected.getMetaData().getColumnCount();
            selected.next();
            return new User(
                    selected.getInt(1),
                    selected.getString(2),
                    selected.getString(3),
                    selected.getString(4));
        } catch (Exception e) {
            System.out.println("SelectUserError");
            System.exit(-104);
        }
        return null;
    }
}
