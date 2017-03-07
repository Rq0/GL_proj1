/**
 * Created by rq0 on 06.03.2017.
 */
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import static java.lang.System.exit;


public class Main {
    public static void  main(String args[]) {
        System.out.println("Create completed");

        User First = new User(1,"FirstLogin","FirstPass");
        User Sec = new User(2,"SecLogin","SecPass");
        ArrayList<User>Users = new ArrayList();
        Users.add(First);
        Users.add(Sec);
        GetUser(Users);

    }

    private static void GetUser(ArrayList<User> users) {
        for (int i = 0; i < 2; i++) {
            System.out.println(users.get(i).login);
            System.exit(0);
        }
        for (int i = 0; i < 2; i++) {
            System.out.println(users.get(i).pass);
        }
    }
}
