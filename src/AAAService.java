import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


class AAAService {

    User getUser(int id) {
        try {
            DbContext dbContext = new DbContext();
            dbContext.connect();
            UserDAO userDAO = new UserDAO();
            return userDAO.selectUser(id, dbContext);
        } catch (Exception e) {
            System.exit(401);
        }
        return null;
    }

    String getUsers() {
        DbContext dbContext = new DbContext();
        dbContext.connect();
        StringBuilder out = new StringBuilder();
        int count = dbContext.count("users") + 1;
        for (int i = 1; i < count; i++) {
            User user = getUser(i);
            out.append(String.format("ID пользователя: %s; Логин: %s; Пароль: %s;\n", user.id, user.login, user.pass));
        }
        return out.toString();
    }

    String getResources() {
        StringBuilder out = new StringBuilder();
        ResourceDAO resourceDAO = new ResourceDAO();
        DbContext dbContext = new DbContext();
        dbContext.connect();

        for (int i = 1; i < dbContext.count("RESOURCES") + 1; i++) {

            Resource resource = resourceDAO.selectResource(i, dbContext);

            out.append(String.format("Ресурс: %s; Роль: %s; ID пользователя: %s; \n", resource.path, resource.role, resource.user.id));
        }
        return out.toString();
    }

    String getAccounts() {
        StringBuilder out = new StringBuilder();
        AccountDAO accountDAO = new AccountDAO();
        DbContext dbContext = new DbContext();
        dbContext.connect();
//nullPE
        for (int id = 1; id < dbContext.count("ACCOUNTS") + 1; id++) {
            Account account1 = accountDAO.selectAccount(id, dbContext);
            out.append(String.format("ID ресурса: %s; дата начала: %s; дата окончания: %s; объем: %s; \n", account1.resourceId, account1.ds, account1.de, account1.vol));
        }
        return out.toString();
    }

    int findUser(UserInput userInput) {
        DbContext dbContext = new DbContext();
        dbContext.connect();
//        UserDAO userDAO = new UserDAO();
//        userDAO.selectUser(0, "and login = " + userInput.login, dbContext);
        int count = dbContext.count("USERS") + 1;
        for (int i = 1; i < count; i++) {
            if (userInput.login.equals(getUser(i).login)) {
                return i;
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
        DbContext dbContext = new DbContext();
        dbContext.connect();
        int count = dbContext.count("USERS") + 1;
        for (int i = 1; i < count; i++) {
            if (userInput.login.equals(getUser(i).login)) {
                if ((md5Hex(md5Hex(userInput.pass) + getUser(i).salt).equals(getUser(i).pass))) {
                    System.out.println("Authentication complete");
                    return true;
                }
            }
        }
        System.exit(2);
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
            try {
                DbContext dbContext = new DbContext();
                dbContext.connect();
                Account account = new Account(
                        dbContext.count("ACCOUNTS") + 1,
                        (dbContext.getResourceFromBase(userInput)).id,
                        Integer.parseInt(userInput.vol),
                        newDate.parse(userInput.ds),
                        newDate.parse(userInput.de));

                AccountDAO accountDAO = new AccountDAO();
                accountDAO.addAccount(account, dbContext);
                dbContext.dispose();
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