package main.java.services;

import main.java.dao.AccountDAO;
import main.java.dao.ResourceDAO;
import main.java.dao.UserDAO;
import main.java.domains.Account;
import main.java.domains.User;
import main.java.domains.UserInput;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {

    private static final Logger log = LogManager.getLogger();

    public User getUser(int id) {
        try {
            return new UserDAO().selectUserById(id);
        } catch (Exception e) {
            log.warn("Пользователя с id - {} не найден", id);
        }
        return null;
    }

    Integer getAccess(UserInput userInput) {
        String[] masOfPath = userInput.res.split("\\."); //разбиваем путь по уровням
        String findPath = "";
        Integer userId = findUser(userInput);
        try {
            for (String string : masOfPath) {
                findPath += string; //опускаемся на уровень ниже
                if (new ResourceDAO().haveAccess(findPath, userInput.role, userId)) {
                    return (new ResourceDAO().getResource(userInput.res, userId)).id;
                }
            }
        } catch (NullPointerException e) {
            log.warn("Нет доступа для {}", userInput.login);
        }
        System.exit(4);
        return null;
    }

    Integer findUser(UserInput userInput) {
        int id;
        try {
            id = new UserDAO().selectUserByLogin(userInput.login).id;
            log.info("Пользователь {} найден", userInput.login);
            return id;
        } catch (Exception e) {
            log.warn("Пользователь {} не найден в бд", userInput.login);
            System.exit(1);
            return null;
        }
    }

    private String addSalt() {
        log.info("Добавляем salt");
        return RandomStringUtils.randomAscii(8);
    }

    private String addHash(String password, String salt) {
        log.info("Добавляем hash");
        return md5Hex(md5Hex(password) + salt);
    }

    boolean checkPass(UserInput userInput) {
        Integer userId = findUser(userInput);
        if ((md5Hex(md5Hex(userInput.pass) + getUser(userId).salt).equals(getUser(userId).pass))) {
            log.info("Authentication complete {}", userInput.login);
            return true;
        }
        log.warn("Пароль для {} введен не правильно", userInput.login);
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
            log.info("Даты валидны");
            return true;
        } catch (Exception e) {
            log.warn("Unreachable date format");
            System.exit(5);
            return false;
        }
    }

    private boolean isVolValid(String vol) {
        try {
            Integer.parseInt(vol);
            log.info("Объем валиден");
            return true;
        } catch (Exception e) {
            log.warn("Unreachable volume format");
            System.exit(5);
            return false;
        }
    }

    boolean addAccount(UserInput userInput) {
        log.info("Начат процесс добавления аккаунта");
        if (isDateValid(userInput.ds, userInput.de) && isVolValid(userInput.vol)) {
            SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = newDate.parse(userInput.ds);
                date2 = newDate.parse(userInput.de);
            } catch (Exception e) {
                log.fatal("Ошибка в добавлении аккаунта {} {}", userInput.login, e.getMessage());
            }
            Account account = new Account(
                    new AccountDAO().getLastAccountId(),
                    getAccess(userInput),
                    Integer.parseInt(userInput.vol),
                    date1,
                    date2);
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.addAccount(account);
            return true;
        } else {
            log.warn("Один из параметров аккаунта {} не валиден", userInput.login);
            return false;
        }
    }
}

