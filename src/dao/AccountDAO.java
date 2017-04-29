package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.DbContext;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class AccountDAO {
    private static final Logger log = LogManager.getLogger();

    private String table = "Accounts";


    public void addAccount(domain.Account account) {
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {
            {
                setLenient(false);
            }
        };
        String paramsValues = String.format("%s, %s, %s, '%s', '%s'", account.id, account.resourceId, account.vol, newDate.format(account.ds), newDate.format(account.de));
        new DbContext().insert(table, paramsValues);
        log.info("Добавлен аккаунт {} в бд", account.id);
    }

    public Integer getLastAccountId(){

        ResultSet selected = new DbContext().select(table, "Max(ID)", "");
        try {
            selected.next();
            selected.getInt(1);
            return selected.getInt(1)+1;
        } catch (Exception e) {
            log.error("GetLastAccountID error; {}");
        }
        return null;
    }
}
