package main.java.services;

import main.java.domains.Role;
import main.java.domains.UserInput;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {

    private CommandLine line;
    private static final Logger log = LogManager.getLogger();

    public void validate(String[] args) {
        UserInput userInput = new UserInput();

        Options options = new Options();
        options.addOption("login", "login", true, "Логин пользователя");
        options.addOption("pass", "password", true, "Пароль пользователя");
        options.addOption("role", "role", true, "Роль пользователя на выбранном ресурсе");
        options.addOption("res", "resource", true, "Адрес ресурса");
        options.addOption("ds", "DateStart", true, "Дата начала");
        options.addOption("de", "DateEnd", true, "Дата окончания");
        options.addOption("vol", "volume", true, "Объем");
        options.addOption("h", "help", false, "Cправка");

        setLine(args, options);
        lineOptions(options, userInput);
    }

    private void setLine(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();
        try {
            line = parser.parse(options, args);
            log.info("Параметры консоли спарсены");
        } catch (Throwable e) {
            printHelp(options);
            log.error("Параметры консоли не парсятся", e);
        }
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("gl_proj", options);
        log.info("Печать справки");
        System.exit(0);
    }


    private void isAccounting(UserInput userInput) {
        userInput.vol = line.getOptionValue("vol");
        userInput.ds = line.getOptionValue("ds");
        userInput.de = line.getOptionValue("de");

        new AAAService().addAccount(userInput);
    }

    private boolean isAuthorisation(UserInput userInput) {
        userInput.res = line.getOptionValue("res");

        try {
            userInput.role = Role.valueOf(line.getOptionValue("role"));
        } catch (Throwable e) {
            log.warn("Несуществующая роль;", e);
            System.exit(3);
        }
        Integer a = new AAAService().getAccess(userInput);
        return (a != null);
    }

    private boolean isAuthentication(UserInput userInput) {
        userInput.login = line.getOptionValue("login");
        Integer userID = new AAAService().findUser(userInput);
        if (line.hasOption("pass") && userID != null) {
            userInput.pass = line.getOptionValue("pass");
            return new AAAService().checkPass(userInput);
        }
        return false;
    }

    private void lineOptions(Options options, UserInput userInput) {

        boolean authentication = false;
        boolean authorisation = false;
        if (line.hasOption("h") || line.getOptions().length == 0) {
            printHelp(options);
        }
        if (line.hasOption("login")) {
            authentication = isAuthentication(userInput);
            log.info("Этап аутентификации прошел");
        }
        if (line.hasOption("res") && line.hasOption("role") && authentication) {
            authorisation = isAuthorisation(userInput);
            log.info("Этап авторизации прошел");
        }
        if (line.hasOption("ds") && line.hasOption("de") && line.hasOption("vol") && (authorisation)) {
            isAccounting(userInput);
            log.info("Этап аккаунтинга прошел");
        }
    }
}