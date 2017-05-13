package main.java.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DbContext {


    private PreparedStatement getStatement() {
        return statement;
    }

    private PreparedStatement statement;

    public Connection getConnection() {
        return connection;
    }

    private Connection connection;

    private static final Logger log = LogManager.getLogger();

    public DbContext() {

    }

    public void connect() {
        try {

            connection = DriverManager.getConnection("jdbc:h2:./GL_proj1", "sa", "");
        } catch (Exception e) {
            log.fatal("Не соединились с бд");
        }
    }
}
