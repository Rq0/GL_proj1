import java.util.Date;

/**
 * Класс аккантов
 * Created by rq0 on 10.03.2017.
 */
class Account {
    Date ds;
    Date de;
    Integer userId;
    Integer vol;

    Account(int userId) {
        this.userId = userId;
    }

    Account(Integer userId, Integer vol,Date ds,Date de){
        this.userId = userId;
        this.vol = vol;
        this.ds = ds;
        this.de = de;
    }


}
