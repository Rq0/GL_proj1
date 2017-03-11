/**
 * Класс ресурсов
 * Created by rq0 on 09.03.2017.
 */
public class Resource {
    String path;
    int role;
    User user;
    int resourceID;

    public Resource(int resourceID, String path, User user, int role) {
        this.resourceID = resourceID;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
