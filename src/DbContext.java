import java.sql.*;

class DbContext {

    private void setStatement(Statement statement) {
        this.statement = statement;
    }

    private Statement getStatement() {
        return statement;
    }

    private Statement statement;

    private Connection getConnection() {
        return connection;
    }

    private Connection connection;

    DbContext() {

    }

    void Connect() {
        try {
            //tcp://localhost/~/test    ./GL_proj1
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        } catch (Exception e) {
            System.exit(434);
        }
    }

    void CreateTable(String tableName, String tableParams) {
        try {
            setStatement(getConnection().createStatement());
            String sqlCreateQuery = "create table IF NOT EXISTS " + tableName + " (" + tableParams + ")";
            getStatement().execute(sqlCreateQuery);
        } catch (Exception e) {
            System.out.println(111);
            System.exit(10);
        }
    }

    void Insert(String tableName, String values) {

        String sqlInsertQuery = "insert into " + tableName + " values(" + values + ")";
        try {
            getStatement().execute(sqlInsertQuery);
        } catch (Exception e) {
            System.exit(11);
        }
    }

    User SelectUser(String tableName, String values, String filter) {
        try {

            setStatement(getConnection().createStatement());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlSelectQuery = "Select " + values + " From " + tableName + " " + filter;
        ResultSet selected;
        try {
            selected = getStatement().executeQuery(sqlSelectQuery);

            return new User(
                    selected.getInt(1),
                    selected.getString(2),
                    selected.getString(3),
                    selected.getString(4));

        } catch (Exception e) {
            System.exit(-104);
        }
        return null;
    }

    Resource SelectResource(String tableName, String values, String filter) {
        try {
            setStatement(getConnection().createStatement());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlSelectQuery = "Select " + values + " From " + tableName + " " + filter;
        ResultSet selected;
        try {
            selected = getStatement().executeQuery(sqlSelectQuery);

            return new Resource(
                    selected.getInt(1),
                    selected.getString(2),
                    SelectUser("Users", "id, login, pass, salt", "where id = " + selected.getInt(3)),
                    Role.valueOf(selected.getString(4)));

        } catch (Exception e) {
            System.exit(404);
        }
        return null;
    }

    Account SelectAccount(String tableName, String values, String filter) {
        try {
            setStatement(getConnection().createStatement());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlSelectQuery = "Select " + values + " From " + tableName + " " + filter;
        ResultSet selected;
        try {
            selected = getStatement().executeQuery(sqlSelectQuery);

            return new Account(
                    selected.getInt(1),
                    selected.getInt(2),
                    selected.getDate(3),
                    selected.getDate(4));

        } catch (Exception e) {
            System.exit(4042);
        }
        return null;
    }

    void Dispose() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("не закрылось");
        }
    }
}
