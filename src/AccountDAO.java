import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;

class AccountDAO {
    private Connection connection;

    AccountDAO(Connection connection) {
        this.connection = connection;
    }

    void AddAccount(Account account) {
        String table = "Accounts";
        String params = "userId int, vol int, ds date, de date";
        String paramsValues;
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {
            {
                setLenient(false);
            }
        };
        try
        {
            Statement statement;
            statement = connection.createStatement();
            String sqlCreateQuery = "create table IF NOT EXISTS " + table + " (" + params + ")";
            statement.execute(sqlCreateQuery);

            paramsValues = String.format("%s, %s, '%s', '%s'", account.userId, account.vol, newDate.format(account.ds), newDate.format(account.de));
            String sqlInsertQuery = "insert into " + table + " values(" + paramsValues + ")";
            statement.execute(sqlInsertQuery);
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }
}
