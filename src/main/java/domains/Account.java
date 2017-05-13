package main.java.domains;

import java.util.Date;

/**
 * Created by rq0 on 01.05.2017.
 */
public class Account {
    public Integer id;
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
