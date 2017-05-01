package main.java.dao;

import main.java.domains.Account;
import main.java.services.DbContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class AccountDAO {
    private static final Logger log = LogManager.getLogger();

    private String table = "Accounts";


    public void addAccount(Account account) {
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {
            {
                setLenient(false);
            }
        };
        String paramsValues = String.format("%s, %s, %s, '%s', '%s'", account.id, account.resourceId, account.vol, newDate.format(account.ds), newDate.format(account.de));
        new DbContext().insert(table, paramsValues);
        log.info("Добавлен аккаунт {} в бд", account.id);
    }

    public Integer getLastAccountId() {

        ResultSet selected = new DbContext().select(table, "Max(ID)", "");
        try {
            selected.next();
            selected.getInt(1);
            Integer id = selected.getInt(1) + 1;
            return id;
        } catch (NullPointerException e) {
            log.fatal("NPE в добавлении аккаунта");
        } catch (Exception e) {
            log.error("GetLastAccountID error; {}");
        }
        return null;
    }
}
