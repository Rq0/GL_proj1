import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * AAAService
 * Весь хлам, что не попал никуда
 * Created by rq0 on 09.03.2017.
 */
class AAAService {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Resource> resources = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();

    //Временная необходимость(наверно)
    void AddUser(int id, String login, String pass) {
        String salt = addSalt();
        users.add(new User(id, login, addHash(pass, salt), salt));
    }

    User GetUser(int id) {
        return users.get(id);
    }

    //correct
    int FindUser(UserInput userInput) {

        for (User user : users) {
            if (userInput.login.equals(user.login)) {
                return user.id;
            }
        }
        System.exit(1);
        return -1;
    }

    private String addSalt() {
        return RandomStringUtils.randomAscii(8);
    }

    private String addHash(String password, String salt) {
        return md5Hex(md5Hex(password) + salt);
    }

    boolean CheckPass(UserInput userInput) {

        for (User user :
                users) {
            if (userInput.login.equals(user.login)) {
                if ((md5Hex(md5Hex(userInput.pass) + user.salt)).equals(user.pass)) {
                    return true;
                } else {
                    System.exit(2);
                }
            }
        }
        return false;
    }

    void AddResource(int id, String path, User user, int role) {
        Resource resource = new Resource(id, path, user, role);
        resources.add(resource);
    }


    //проверка доступа к ресурсу
    boolean CheckRole(UserInput userInput) {
        for (Resource res :
                resources) {
            if (res.user.equals(users.get(FindUser(userInput)))) {
                if (res.role == Integer.parseInt(userInput.role)) {
                    return res.path.equals(userInput.res) || ExtendRole(userInput);
                }
            }
        }
        return false;
    }

    //наследование роли для дочерних ресурсов
    private boolean ExtendRole(UserInput userInput) {
        while (userInput.res.contains(".")) {
            userInput.res = userInput.res.substring(0, userInput.res.lastIndexOf('.'));
            if (CheckRole(userInput)) {
                return true;
            }
        }
        System.exit(4);
        return false;
    }

    boolean AddAccount(UserInput userInput) {
        Account account = new Account(FindUser(userInput));
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-mm-dd") {{
            setLenient(false);
        }};
        try {
            account.ds = newDate.parse(userInput.ds);
            account.de = newDate.parse(userInput.de);
        } catch (Exception e) {
            System.exit(5);
            return false;
        }
        try {
            account.vol = Integer.parseInt(userInput.vol);
        } catch (Exception e) {
            System.exit(5);
            return false;
        }
        accounts.add(account);
        return true;
    }


    protected enum Role {
        READ, WRITE, EXECUTE
    }
}
