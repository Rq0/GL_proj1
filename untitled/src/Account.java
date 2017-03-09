import java.util.Date;

/**
 * Created by rq0 on 10.03.2017.
 */
public class Account {
    Date ds,de;
    int userID, value;
    public Account(int userID, Date ds, Date de, int value){
        this.userID = userID;
        this.ds = ds;
        this.de = de;
        this.value = value;
    }
}
