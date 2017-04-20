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

    ResultSet Select(String tableName, String values, String filter) {
        try {
            setStatement(getConnection().createStatement());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlSelectQuery = "Select " + values + " From " + tableName + " " + filter;
        ResultSet selected;
        try {
            selected = getStatement().executeQuery(sqlSelectQuery);
            return selected;

        } catch (Exception e) {
            System.exit(404);
        }
        return null;
    }

    int Count(String tableName) {
        String sqlSelectQuery = "Select count(id) From " + tableName;

        try {
            setStatement(getConnection().createStatement());
            ResultSet resultSet = getStatement().executeQuery(sqlSelectQuery);
            resultSet.last();
            return resultSet.getInt(1);
        } catch (Exception e) {
            System.out.println("CountException");
            e.printStackTrace();
        }
        return 1;
    }


    Resource getResourceFromBase(UserInput userInput) {
        AAAService aaaService = new AAAService();
        try {
            Connect();
            setStatement(getConnection().createStatement());
            String[] masOfPath = userInput.res.split("\\."); //разбиваем путь по уровням
            boolean access = false;
            String findPath = "";
            for (String string : masOfPath) {
                findPath += string; //опускаемся на уровень ниже
                {
                    ResultSet result = statement.executeQuery(String.format("SELECT * FROM RESOURCES where path like '%s' ", findPath)
                            + String.format(" and role like '%s", userInput.role) + String.format("' and userid like %s", aaaService.findUser(userInput)));
                    if (result.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                        access = true;
                        break;
                    }
                }
            }
            if (access) {
                ResultSet result = statement.executeQuery(String.format("SELECT * FROM RESOURCES where path like '%s'", userInput.res)); //получаем тот ресурс который запрашивали
                result.next();
                return new Resource(Integer.valueOf(result.getString(0)), result.getString(2), aaaService.getUser(result.getInt(1)), userInput.role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Resource();
    }

    void Dispose() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("не закрылось");
        }
    }
}
