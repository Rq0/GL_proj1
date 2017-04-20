import java.util.Date;

/**
 * Класс аккантов
 * Created by rq0 on 10.03.2017.
 */
class Account {
    Integer id;
    Integer resourceId;
    Integer vol;
    Date ds;
    Date de;


    Account() {

    }

    Account(Integer id, Integer resourceId, Integer vol, Date ds, Date de) {
        this.id = id;
        this.resourceId = resourceId;
        this.vol = vol;
        this.ds = ds;
        this.de = de;
    }


}
