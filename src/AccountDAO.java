import java.sql.ResultSet;
import java.text.SimpleDateFormat;

class AccountDAO {

    void AddAccount(Account account, DbContext dbContext) {
        String table = "Accounts";
        String params = "userId int, vol int, ds date, de date";
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {
            {
                setLenient(false);
            }
        };
        String paramsValues = String.format("%s, %s, '%s', '%s'", account.userId, account.vol, newDate.format(account.ds), newDate.format(account.de));
        dbContext.CreateTable(table, params);
        dbContext.Insert(table, paramsValues);
        dbContext.Dispose();
    }
    Account SelectAccount(int accountId,DbContext dbContext){
        String table = "Accounts";
        String params = "USERID, VOL,DS,DE  ";
        String filter="where USERID = " +accountId ;
        ResultSet selected = dbContext.Select(table,params,filter);

        try {
            int columns = selected.getMetaData().getColumnCount();
            while (selected.next()) {
                for (int i = 1; i <= columns; i++) {
                    return new Account(
                            selected.getInt(1),
                            selected.getInt(2),
                            selected.getDate(3),
                            selected.getDate(4));
                }
            }
        }
        catch (Exception e){
            System.out.println("SelectAccountError");
            System.exit(4042);
        }
        return null;
    }
}
