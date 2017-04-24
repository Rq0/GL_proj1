package dao;

import domain.Account;
import services.DbContext;

import services.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class AccountDAO {
    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public void addAccount(Account account) {
        String table = "Accounts";
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {
            {
                setLenient(false);
            }
        };
        String paramsValues = String.format("%s, %s, %s, '%s', '%s'", account.id, account.resourceId, account.vol, newDate.format(account.ds), newDate.format(account.de));
        new DbContext().insert(table, paramsValues);
        log.info("Добавлен аккаунт {} в бд", account.id);
    }

    Account selectAccount(int accountId) {
        String table = "Accounts";
        String params = "ID, RESOURCEID, VOL, DS, DE";
        String filter = "where ID = " + accountId;
        ResultSet selected = new DbContext().select(table, params, filter);

        try {
            selected.next();
            return new Account(
                    selected.getInt(1),
                    selected.getInt(2),
                    selected.getInt(3),
                    selected.getDate(4),
                    selected.getDate(5));
        } catch (Exception e) {
            log.error("SelectAccount {} error; {}", accountId, e.getMessage());
        }
        return null;
    }
}
