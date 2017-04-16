import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    void Dispose() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("не закрылось");
        }
    }
}
