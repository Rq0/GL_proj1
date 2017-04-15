

class UserDAO {
    void AddUser(int id, String login, String pass, String salt, DbContext dbContext) {
        String table = "Users";
        String params = "id int, login varchar(255) primary key, pass varchar(255), salt varchar(255)";

        String paramsValues = String.format("%s,'%s', '%s','%s'", id, login, pass, salt);
        dbContext.CreateTable(table, params);
        dbContext.Insert(table, paramsValues);
    }
}
