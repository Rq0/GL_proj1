import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

/**AAAService
 * Весь хлам, что не попал никуда
 * Created by rq0 on 09.03.2017.
 */
public class AAAService {
    public int FindUser(ArrayList<User> Users, UserInput userInput){
        int id = -1;
        for (User User : Users) {
            if (userInput.login.equals(User.login)) {
                id = User.ID;
            }
        }
        if(id==-1){
            System.exit(1);
        }
        return id;
    }
    public boolean CheckPass(ArrayList<User> Users, UserInput userInput, int id){

        if (userInput.pass.equals(Users.get(id).pass)) {
            return true;

        } else {
            System.exit(2);
            return false;
        }
    }
}