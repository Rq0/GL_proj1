import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

class DbContext {

    private void setStatement(PreparedStatement statement) {
        this.statement = statement;
    }

    private PreparedStatement getStatement() {
        return statement;
    }

    private PreparedStatement statement;

    private Connection getConnection() {
        return connection;
    }

    private Connection connection;

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    DbContext() {

    }

    void connect() {
        try {
            /**
             * Возможные варианты:
             * //tcp://localhost/~/test
             * ./GL_proj1
             */
            connection = DriverManager.getConnection("jdbc:h2:./GL_proj1", "sa", "");
        } catch (Exception e) {
            log.fatal("Не соединились с бд: {}", e.getMessage());
            System.exit(434);
        }
    }

    void createTable(String tableName, String tableParams) {
        try {

            String sqlCreateQuery = "create table IF NOT EXISTS " + tableName + " (" + tableParams + ")";
            getConnection().prepareStatement(sqlCreateQuery).executeQuery();
            getStatement().execute(sqlCreateQuery);
            log.info("Создана таблица бд");
        } catch (Exception e) {
            log.error("Не смогли создать таблицу бд: {}", e.getMessage());
            System.exit(10);
        }
    }

    void insert(String tableName, String values) {

        String sqlInsertQuery = "insert into " + tableName + " values(" + values + ")";
        try {
            getConnection().prepareStatement(sqlInsertQuery).executeUpdate();
           // getStatement().execute(sqlInsertQuery);
            log.info("Прошла вставка в бд");
        } catch (Exception e) {
            log.error("Не прошла вставка в бд: {}", e.getMessage());
            System.exit(11);
        }
    }

    ResultSet select(String tableName, String values, String filter) {

        String sqlSelectQuery = "Select " + values + " From " + tableName + " " + filter;
        ResultSet selected;
        try {

            //setStatement(getConnection().createStatement());
            selected = getConnection().prepareStatement(sqlSelectQuery).executeQuery();
                   // getStatement().executeQuery(sqlSelectQuery);
            return selected;

        } catch (Exception e) {
            log.error("Не прошла выборка:{}, ошибка: {}", sqlSelectQuery, e.getMessage());
            System.exit(404);
        }
        return null;
    }

    int count(String tableName) {
        String sqlSelectQuery = "Select count(id) From " + tableName;

        try {
            ResultSet resultSet = getConnection().prepareStatement(sqlSelectQuery).executeQuery();
            resultSet.last();
            return resultSet.getInt(1);
        } catch (Exception e) {
            log.error("Не удалось подсчитать количество строк в таблице {}; {}",tableName,e.getMessage());
        }
        return 1;
    }


    Resource getResourceFromBase(UserInput userInput) {
        AAAService aaaService = new AAAService();
        try {
            connect();

            String[] masOfPath = userInput.res.split("\\."); //разбиваем путь по уровням
            boolean access = false;
            String findPath = "";
            for (String string : masOfPath) {
                findPath += string; //опускаемся на уровень ниже
                {

                    ResultSet result = getConnection().prepareStatement(String.format("SELECT * FROM RESOURCES where path like '%s' ", findPath)
                            + String.format(" and role like '%s", userInput.role) + String.format("' and userid like %s", aaaService.findUser(userInput))).executeQuery();
                    if (result.next()) { //проверяем вернулся ли хоть 1 ресурс с таким доступом
                        access = true;
                        break;
                    }
                }
            }
            if (access) {
                ResultSet result = getConnection().prepareStatement(String.format("SELECT * FROM RESOURCES where path like '%s'", userInput.res)).executeQuery(); //получаем тот ресурс который запрашивали
                result.next();
                return new Resource(
                        result.getInt("ID"),
                        result.getString(2),
                        aaaService.getUser(result.getInt(1)),
                        userInput.role);
            }
        } catch (SQLException e) {
            log.error("Не прошло определение доступа к ресурсу {}; ",userInput.res, e.getMessage());
        }
        return new Resource();
    }

    void dispose() {
        try {
            statement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("не закрылось, {}",e.getMessage());
        }
    }
}
