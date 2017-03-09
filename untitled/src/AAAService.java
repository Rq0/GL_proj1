import javax.annotation.*;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

/**AAAService
 * Весь хлам, что не попал никуда
 * Created by rq0 on 09.03.2017.
 */
public class AAAService {
    public int FindUser(ArrayList<User> Users, UserInput userInput){
        int id = -1;
        for (User user : Users) {
            if (userInput.login.equals(user.login)) {
                id = user.ID;
            }
        }
        if(id==-1){
            System.exit(1);
        }
        return id;
    }
    public boolean CheckPass(ArrayList<User> Users, UserInput userInput, int id){

        for (User user :
                Users) {
            if(userInput.login.equals(user.login)){
                if(userInput.pass.equals(user.pass)){
                    return true;
                }
                else{
                    System.exit(2);
                }
            }
        }
        return false;
    }
    public boolean CheckRole(ArrayList<User>Users,ArrayList<Resource> Resources, UserInput userInput){
        for (Resource res:
             Resources) {
            if(res.path.equals(userInput.res)){
                if(res.user.equals(Users.get(FindUser(Users,userInput)))){
                    if (res.role==userInput.role)
                        return true;
                }
            }

        }
        System.exit(4);
        return false;
    }
    public boolean GetAccount(ArrayList<User> Users, UserInput userInput){

        return false;
    }
}
