package domain;

import java.util.Date;

/**
 * Класс аккантов
 * Created by rq0 on 10.03.2017.
 */
public class Account {
    public final Integer id;
    public final Integer resourceId;
    public final Integer vol;
    public final Date ds;
    public final Date de;

    public Account(Integer id, Integer resourceId, Integer vol, Date ds, Date de) {
        this.id = id;
        this.resourceId = resourceId;
        this.vol = vol;
        this.ds = ds;
        this.de = de;
    }


}
