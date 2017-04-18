import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


class AAAService {

    User getUser(int id) {
        try {
            DbContext dbContext = new DbContext();
            dbContext.Connect();
            UserDAO userDAO = new UserDAO();
            return userDAO.SelectUser(id, "", dbContext);
        } catch (Exception e) {
            System.exit(401);
        }
        return null;
    }


    String getUsers() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            User user = getUser(i);
            out.append(String.format("ID пользователя: %s; Логин: %s; Пароль: %s;\n", user.id, user.login, user.pass));
        }
        return out.toString();
    }


    String getResources() {
        StringBuilder out = new StringBuilder();
        ResourceDAO resourceDAO = new ResourceDAO();
        DbContext dbContext = new DbContext();
        dbContext.Connect();
        int id = -1;
//        for (Resource resource : resources) {
//
//
//            id++;
////            resourceDAO.SelectResource(id,dbContext);
//
//            out.append(String.format("Ресурс: %s; Роль: %s; ID пользователя: %s; \n", resource.path, resource.role, resource.user.id));
//        }
        return out.toString();
    }

    String getAccounts() {
        StringBuilder out = new StringBuilder();
        AccountDAO accountDAO = new AccountDAO();
        DbContext dbContext = new DbContext();
        dbContext.Connect();

        for (int id = 0; id < 1; id++) {
            Account account1 = accountDAO.SelectAccount(id, dbContext);
            out.append(String.format("ID пользователя: %s; дата начала: %s; дата окончания: %s; объем: %s; \n", account1.userId, account1.ds, account1.de, account1.vol));
        }
        return out.toString();
    }


    void addUser(int id, String login, String pass) {
        String salt = addSalt();

//        DbContext dbContext = new DbContext();
//        dbContext.Connect();
//        UserDAO userDAO = new UserDAO();
//        userDAO.AddUser(id, login, addHash(pass, salt), salt, dbContext);
//        dbContext.Dispose();
    }


    int findUser(UserInput userInput) {
        DbContext dbContext = new DbContext();
        UserDAO userDAO = new UserDAO();
        userDAO.SelectUser(0, "and login = " + userInput.login, dbContext);

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
        try {
            DbContext dbContext = new DbContext();
            dbContext.Connect();
            ResourceDAO resourceDAO = new ResourceDAO();
            resourceDAO.AddResource(id,path,user,role, dbContext);
            dbContext.Dispose();
        } catch (Exception e) {
            System.exit(404);
        }
    }

    /**
     * проверка доступа к ресурсу
     *
     * @param userInput входные параметры приложения
     * @return Нашел ли нужный ресурс с ролью
     */
//    boolean checkRole(UserInput userInput) {
//        for (Resource res : resources) {
//            if (res.user.equals(users.get(findUser(userInput)))) {
//                if (res.role.equals(userInput.role)) {
//                    return res.path.equals(userInput.res) || extendRole(userInput);
//                }
//            }
//        }
//        return false;
//    }

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

    private boolean isDateValid(String ds, String de) {
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            newDate.parse(ds);
            newDate.parse(de);
            return true;
        } catch (Exception e) {
            System.out.println("Unreachable date format");
            System.exit(5);
            return false;
        }
    }

    private boolean isVolValid(String vol) {
        try {
            Integer.parseInt(vol);
            return true;
        } catch (Exception e) {
            System.out.println("Unreachable volume format");
            System.exit(5);
            return false;
        }
    }

    boolean addAccount(UserInput userInput) {
        if (isDateValid(userInput.ds, userInput.de) && isVolValid(userInput.vol)) {
            SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
                try{
                    Account account = new Account(1, Integer.parseInt(userInput.vol), newDate.parse(userInput.ds), newDate.parse(userInput.de));
                    DbContext dbContext = new DbContext();
                    dbContext.Connect();
                    AccountDAO accountDAO = new AccountDAO();
                    accountDAO.AddAccount(account, dbContext);
                }
                catch (Exception e) {
                    System.exit(434);
                }
                System.out.println("Accounting complete");
                return true;
            }
        else{
            return false;
        }
    }
}