import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


class AAAService {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Resource> resources = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();

    User getUser(int id) {
        return users.get(id);
    }


    String getUsers() {
        StringBuilder out = new StringBuilder();
        for (User user : users) {
            out.append(String.format("ID пользователя: %s; Логин: %s; Пароль: %s;\n", user.id, user.login, user.pass));
        }
        return out.toString();
    }


    String getResources() {
        StringBuilder out = new StringBuilder();
        for (Resource resource : resources) {
            out.append(String.format("Ресурс: %s; Роль: %s; ID пользователя: %s; \n", resource.path, resource.role, resource.user.id));
        }
        return out.toString();
    }

    String getAccounts() {
        StringBuilder out = new StringBuilder();
        for (Account account : accounts) {
            out.append(String.format("ID пользователя: %s; дата начала: %s; дата окончания: %s; объем: %s; \n", account.userId, account.ds, account.de, account.vol));
        }
        return out.toString();
    }


    void addUser(int id, String login, String pass) {
        String salt = addSalt();
        users.add(new User(id, login, addHash(pass, salt), salt));

        DbContext dbContext = new DbContext();
        dbContext.Connect();
        UserDAO userDAO = new UserDAO();
        userDAO.AddUser(id, login, addHash(pass, salt), salt, dbContext);
        dbContext.Dispose();
    }


    int findUser(UserInput userInput) {

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

    boolean checkPass(UserInput userInput) {
        for (User user : users) {
            if (userInput.login.equals(user.login)) {
                if ((md5Hex(md5Hex(userInput.pass) + user.salt)).equals(user.pass)) {
                    System.out.println("Authentication complete");
                    return true;
                } else {
                    System.exit(2);
                }
            }
        }
        return false;
    }


    void addResource(int id, String path, User user, Role role) {
        resources.add(new Resource(id, path, user, role));
    }

    /**
     * проверка доступа к ресурсу
     *
     * @param userInput входные параметры приложения
     * @return Нашел ли нужный ресурс с ролью
     */
    boolean checkRole(UserInput userInput) {
        for (Resource res : resources) {
            if (res.user.equals(users.get(findUser(userInput)))) {
                if (res.role.equals(userInput.role)) {
                    return res.path.equals(userInput.res) || extendRole(userInput);
                }
            }
        }
        return false;
    }

    /**
     * наследование роли для дочерних ресурсов
     *
     * @param userInput входные параметры приложения
     * @return Есть ли нужная роль у родителя
     */
    private boolean extendRole(UserInput userInput) {
        while (userInput.res.contains(".")) {
            userInput.res = userInput.res.substring(0, userInput.res.lastIndexOf('.'));
            if (checkRole(userInput)) {
                return true;
            }
        }
        System.exit(4);
        return false;
    }

    private boolean isDateValid(Account account, String ds, String de) {
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            account.ds = newDate.parse(ds);
            account.de = newDate.parse(de);
            return true;
        } catch (Exception e) {
            System.out.println("Unreachable date format");
            System.exit(5);
            return false;
        }
    }

    private boolean isVolValid(Account account, String vol) {
        try {
            account.vol = Integer.parseInt(vol);
            return true;
        } catch (Exception e) {
            System.out.println("Unreachable volume format");
            System.exit(5);
            return false;
        }
    }

    boolean addAccount(UserInput userInput) {
        Account account = new Account(findUser(userInput));
        if (isDateValid(account, userInput.ds, userInput.de) && isVolValid(account, userInput.vol)) {
            accounts.add(account);
            try {
                DbContext dbContext = new DbContext();
                dbContext.Connect();
                AccountDAO accountDAO = new AccountDAO();
                accountDAO.AddAccount(account, dbContext);
                dbContext.Dispose();
            } catch (Exception e) {
                System.exit(434);
            }
            System.out.println("Accounting complete");
            return true;
        } else {
            return false;
        }
    }
}
