import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

/**AAAService
 * Весь хлам, что не попал никуда
 * Created by rq0 on 09.03.2017.
 */
public class AAAService {
    public int FindUser(ArrayList<User> Users, UserInput userInput){
        int id = -1;
        for (User User : Users) {       //приглядеться к форичу
            if (userInput.login.compareTo(User.login) == 0)
                id = User.ID;
        }
        if(id==-1) System.exit(404);
        return id;
    }
    public boolean CheckPass(ArrayList<User> Users, UserInput userInput, int id){

        int ArrID = -1;
        for (int i = 0; i < Users.size(); i++) {
            if(Users.get(i).ID.compareTo(id)==0)
                ArrID=i;
        }
        if (ArrID==-1)
            System.exit(404);
        return userInput.pass.compareTo(Users.get(ArrID).pass) == 0;
    }
}
