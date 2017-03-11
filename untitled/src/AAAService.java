import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * AAAService
 * Весь хлам, что не попал никуда
 * Created by rq0 on 09.03.2017.
 */
public class AAAService {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Resource> resources = new ArrayList<>();
    private static ArrayList<Account> accounts = new ArrayList<>();

    //Временная необходимость(наверно)
    protected void AddUser(int id, String login, String pass, String salt) {
        User user = new User(id, login, pass, salt);
        addHash(user);
        users.add(user.id, user);
    }

    protected User GetUser(int id) {
        return users.get(id);
    }

    protected String GetUsers() {
        String out = "";
        for (User user :
                users) {
            out += String.format("ID пользователя: %s; Логин: %s; Пароль: %s;\n", user.id, user.login, user.pass);
        }
        return out;
    }

    protected int FindUser(UserInput userInput) {
        int id = -1;
        for (User user : users) {
            if (userInput.login.equals(user.login)) {
                id = user.id;
            }
        }
        if (id == -1) {
            System.exit(1);
        }
        return id;
    }

    String addSalt() {
        return RandomStringUtils.randomAscii(8);

    }

    void addHash(User user) {
        user.pass = md5Hex(md5Hex(user.pass) + user.salt);
    }

    protected boolean CheckPass(UserInput userInput) {

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

    protected void AddResource(int id, String path, User user, int role) {
        Resource resource = new Resource(id, path, user, role);
        resources.add(resource);
    }

    protected String GetResources() {
        String out = "";
        for (Resource resource :
                resources) {
            out += String.format("Ресурс: %s; Роль: %s; ID пользователя: %s; \n", resource.path, resource.role, resource.user.id);
        }
        return out;
    }

    //проверка доступа к ресурсу
    protected boolean CheckRole(UserInput userInput) {
        for (Resource res :
                resources) {

            if (res.user.equals(users.get(FindUser(userInput)))) {
                if (res.role == Integer.parseInt(userInput.role)) {
                    if (res.path.equals(userInput.res)) {
                        return true;
                    } else {
                        return ExtendRole(userInput);
                    }
                }
            }
        }
        return false;
    }

    //наследование роли для дочерних ресурсов
    protected boolean ExtendRole(UserInput userInput) {
        String parentResource = userInput.res;
        UserInput newInput = userInput;
        while (parentResource.contains(".")) {
            parentResource = userInput.res.substring(0, userInput.res.lastIndexOf('.'));
            newInput.res = parentResource;
            if (CheckRole(newInput)) {
                return true;
            }
        }
        System.exit(4);
        return false;
    }

    protected boolean AddAccount(UserInput userInput) {
        Date ds, de;
        int vol;
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-mm-dd") {{
            setLenient(false);
        }};
        try {
            ds = newDate.parse(userInput.ds);
            de = newDate.parse(userInput.de);
            vol = Integer.parseInt(userInput.vol);
            Account account = new Account(FindUser(userInput), ds, de, vol);
            accounts.add(account);
            return true;
        } catch (Exception ignored) {
        }
        System.exit(5);
        return false;
    }

    protected String GetAccounts() {
        String out = "";
        for (Account account :
                accounts) {
            out += String.format("ID пользователя: %s; дата начала: %s; дата окончания: %s; объем: %s; \n", account.userID, account.ds, account.de, account.vol);
        }
        return out;
    }

    protected enum Role {
        READ, WRITE, EXECUTE
    }
}
