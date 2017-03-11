import javax.jws.soap.SOAPBinding;

/**
 * Класс ресурсов
 * Created by rq0 on 09.03.2017.
 */
public class Resource {
    String path;
    int role;
    User user;
    int id;
    public Resource(int id, String path, User user, int role){
        this.id = id;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
