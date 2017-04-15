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
}
