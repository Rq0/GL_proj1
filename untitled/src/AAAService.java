import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**AAAService
 * Весь хлам, что не попал никуда
 * Created by rq0 on 09.03.2017.
 */
public class AAAService {
    private static ArrayList <User> users = new ArrayList<>();
    private static ArrayList<Resource> resources = new ArrayList<>();
    public static ArrayList<Account> accounts = new ArrayList<>();
    private static AAAService aaaService = new AAAService();
    public int FindUser(ArrayList<User> Users, UserInput userInput){
        int id = -1;
        for (User user : Users) {
            if (userInput.login.equals(user.login)) {
                id = user.id;
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
                    if (res.role==Integer.parseInt(userInput.role))
                        return true;
                }
            }

        }
        System.exit(4);
        return false;
    }
    public boolean AddAccount(ArrayList<User> Users, UserInput userInput){
        Date ds, de;
        int vol;
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-mm-dd"){{
            setLenient(false);
        }};
        try {
            ds = newDate.parse(userInput.ds);
            de = newDate.parse(userInput.de);
            vol = Integer.parseInt(userInput.vol);
            Account account = new Account(aaaService.FindUser(Users,userInput),ds,de,vol);
            accounts.add(account);
            return true;
        } catch (Exception e){
        }
        System.exit(5);
        return false;
    }
    public String GetAccounts() {
        String out="";
        for (Account account:
                accounts) {
            out+= String.format("ID пользователя: %s; дата начала: %s; дата окончания: %s; объем: %s; \n",account.userID,account.ds,account.de,account.vol);
        }
        return out;
    }
}
