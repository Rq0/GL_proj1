class ResourceDAO {
    void AddResource(int id, String path, User user, Role role, DbContext dbContext) {
        String table = "Resources";
        String params = "id int,  path varchar(255), userId int, role varchar(255)";

        String paramsValues = String.format("%s,'%s', '%s','%s'", id, path, user.id, role);
        dbContext.CreateTable(table, params);
        dbContext.Insert(table, paramsValues);
    }
}
