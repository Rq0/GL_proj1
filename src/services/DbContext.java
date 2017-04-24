package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DbContext {


    private PreparedStatement getStatement() {
        return statement;
    }

    private PreparedStatement statement;

    private Connection getConnection() {
        return connection;
    }

    private Connection connection;

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public DbContext() {

    }

    private void connect() {
        try {
            /**
             * Возможные варианты:
             * //tcp://localhost/~/test
             * ./GL_proj1
             */
            connection = DriverManager.getConnection("jdbc:h2:./GL_proj1", "sa", "");
        } catch (Exception e) {
            log.fatal("Не соединились с бд: {}", e.getMessage());
        }
    }

    void createTable(String tableName, String tableParams) {
        try {
            connect();
            String sqlCreateQuery = String.format("create table IF NOT EXISTS %s (%s) ", tableName, tableParams);
            getConnection().prepareStatement(sqlCreateQuery).executeQuery();
            getStatement().execute(sqlCreateQuery);
            log.info("Создана таблица бд");
        } catch (Exception e) {
            log.error("Не смогли создать таблицу бд: {}", e.getMessage());
            System.exit(10);
        }
    }

    public void insert(String tableName, String values) {
        connect();
        String sqlInsertQuery = String.format("insert into %s values (%s)", tableName, values);
        try {
            getConnection().prepareStatement(sqlInsertQuery).executeUpdate();
            // getStatement().execute(sqlInsertQuery);
            log.info("Прошла вставка в бд");
        } catch (Exception e) {
            log.error("Не прошла вставка в бд: {}", e.getMessage());
            System.exit(11);
        }
    }

    public ResultSet select(String tableName, String values, String filter) {
        connect();
        String sqlSelectQuery = String.format("Select %s From %s %s ", values, tableName, filter);
        ResultSet selected;
        try {
            selected = getConnection().prepareStatement(sqlSelectQuery).executeQuery();
            return selected;

        } catch (Exception e) {
            log.error("Не прошла выборка:{}, ошибка: {}", sqlSelectQuery, e.getMessage());
            System.exit(404);
        }
        return null;
    }

    int count(String tableName) {
        connect();
        String sqlSelectQuery = String.format("Select max(id) From %s", tableName);

        try {
            ResultSet resultSet = getConnection().prepareStatement(sqlSelectQuery).executeQuery();
            resultSet.last();
            return resultSet.getInt(1);
        } catch (Exception e) {
            log.error("Не удалось подсчитать количество строк в таблице {}; {}", tableName, e.getMessage());
        }
        return 1;
    }

    void dispose() {
        try {
            statement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("не закрылось, {}", e.getMessage());
        }
    }
}
