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
        DbContext dbContext = new DbContext();
        dbContext.Connect();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < dbContext.Count("users"); i++) {
/* i+1 на локалхосте, i в файле */
            User user = getUser(i + 1);
            out.append(String.format("ID пользователя: %s; Логин: %s; Пароль: %s;\n", user.id, user.login, user.pass));
        }
        return out.toString();
    }


    String getResources() {
        StringBuilder out = new StringBuilder();
        ResourceDAO resourceDAO = new ResourceDAO();
        DbContext dbContext = new DbContext();
        dbContext.Connect();

        for (int i = 0; i < dbContext.Count("RESOURCES"); i++) {

            Resource resource = resourceDAO.SelectResource(i + 1, dbContext);

            out.append(String.format("Ресурс: %s; Роль: %s; ID пользователя: %s; \n", resource.path, resource.role, resource.user.id));
        }
        return out.toString();
    }

    String getAccounts() {
        StringBuilder out = new StringBuilder();
        AccountDAO accountDAO = new AccountDAO();
        DbContext dbContext = new DbContext();
        dbContext.Connect();

        for (int id = 0; id < dbContext.Count("ACCOUNTS"); id++) {
            Account account1 = accountDAO.SelectAccount(id, dbContext);
            out.append(String.format("ID ресурса: %s; дата начала: %s; дата окончания: %s; объем: %s; \n", account1.resourceId, account1.ds, account1.de, account1.vol));
        }
        return out.toString();
    }


    void addUser(int id, String login, String pass) {
        String salt = addSalt();

        DbContext dbContext = new DbContext();
        dbContext.Connect();
        UserDAO userDAO = new UserDAO();
        userDAO.AddUser(id, login, addHash(pass, salt), salt, dbContext);
        dbContext.Dispose();
    }


    int findUser(UserInput userInput) {
        DbContext dbContext = new DbContext();
        dbContext.Connect();
//        UserDAO userDAO = new UserDAO();
//        userDAO.SelectUser(0, "and login = " + userInput.login, dbContext);

        for (int i = 0; i < dbContext.Count("USERS"); i++) {
            if (userInput.login.equals(getUser(i+1).login)) {
                return i+1;
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
//        DbContext dbContext = new DbContext();
//        dbContext.Connect();
//        for (int i = 1; i < dbContext.Count("USERS"); i++) {
//            if (userInput.login.equals(getUser(i).login)) {
//                if ((userInput.pass + getUser(i).salt).equals(getUser(i).salt)) {
//                    System.out.println("Authentication complete");
//                    return true;
//                } else {
//
//                }
//            }
//        }
//        System.exit(2);
//        return false;
        return true;
    }

    void addResource(int id, String path, User user, Role role) {
        try {
            DbContext dbContext = new DbContext();
            dbContext.Connect();
            ResourceDAO resourceDAO = new ResourceDAO();
            resourceDAO.AddResource(
                    path,
                    user,
                    role,
                    dbContext);
            dbContext.Dispose();
        } catch (Exception e) {
            System.exit(404);
        }
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
            try {
                DbContext dbContext = new DbContext();
                dbContext.Connect();
                Account account = new Account(
                        findUser(userInput),
                        dbContext.getResourceFromBase(userInput).id,
                        Integer.parseInt(userInput.vol),
                        newDate.parse(userInput.ds),
                        newDate.parse(userInput.de));

                AccountDAO accountDAO = new AccountDAO();
                accountDAO.AddAccount(account, dbContext);
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