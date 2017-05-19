package main.java.dao;

import main.java.domains.Account;
import main.java.services.DbContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDAO {
    private static final Logger log = LogManager.getLogger();

    private String table = "Accounts";


    public void addAccount(Account account) {
        DbContext dbContext = new DbContext();
        dbContext.connect();
        String sqlSelectQuery = "INSERT INTO  Accounts (RESOURCEID, VOL, DS, DE) values (?, ?, ?, ?) ";
        try (PreparedStatement preparedStatement = dbContext.getConnection().prepareStatement(sqlSelectQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, account.resourceId);
            preparedStatement.setInt(2, account.vol);
            preparedStatement.setDate(3, new java.sql.Date(account.ds.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(account.de.getTime()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.id = generatedKeys.getInt(1);
                log.info("Добавлен аккаунт {} в бд", account.id);
            }
        } catch (Exception e) {
            log.fatal("Не прошла вставка аккаунта в бд", e);
        }
    }

    public Integer getLastAccountId() {

        DbContext dbContext = new DbContext();
        dbContext.connect();
        String sqlSelectQuery = "Select Max(ID) From Accounts";

        ResultSet selected;
        try (PreparedStatement preparedStatement = dbContext.getConnection().prepareStatement(sqlSelectQuery)) {
            selected = preparedStatement.executeQuery();
            if (selected.next()) {
                selected.getInt(1);
                return selected.getInt(1) + 1;
            }
        } catch (NullPointerException e) {
            log.fatal("NPE в добавлении аккаунта", e);
        } catch (Exception e) {
            log.error("GetLastAccountID error;", e);
        }
        return null;

    }
}
