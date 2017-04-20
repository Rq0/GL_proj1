import java.sql.ResultSet;
import java.text.SimpleDateFormat;

class AccountDAO {

    void AddAccount(Account account, DbContext dbContext) {
        String table = "Accounts";
        String params = "id int IDENTITY(1,1), resourceId int, ds date, de date, vol int";
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {
            {
                setLenient(false);
            }
        };
        String paramsValues = String.format("%s, %s, '%s', '%s', %s", account.id, account.resourceId, newDate.format(account.ds), newDate.format(account.de),account.vol);
        dbContext.CreateTable(table, params);
        dbContext.Insert(table, paramsValues);
        dbContext.Dispose();
    }

    Account SelectAccount(int accountId, DbContext dbContext) {
        String table = "Accounts";
        String params = "ID, USERID, VOL, DS, DE";
        String filter = "where USERID = " + accountId;
        ResultSet selected = dbContext.Select(table, params, filter);

        try {
            int columns = selected.getMetaData().getColumnCount();
            while (selected.next()) {
                for (int i = 1; i <= columns; i++) {
                    return new Account(
                            selected.getInt(1),
                            selected.getInt(2),
                            selected.getInt(3),
                            selected.getDate(4),
                            selected.getDate(5));
                }
            }
        } catch (Exception e) {
            System.out.println("SelectAccountError");
            System.exit(4042);
        }
        return null;
    }
}
