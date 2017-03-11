import java.util.Date;

/**
 * Класс аккантов
 * Created by rq0 on 10.03.2017.
 */
public class Account {
    Date ds, de;
    int userID, vol;

    public Account(int userID, Date ds, Date de, int vol) {
        this.userID = userID;
        this.ds = ds;
        this.de = de;
        this.vol = vol;
    }
}
