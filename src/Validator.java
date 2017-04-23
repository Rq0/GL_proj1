import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Validator {

    private CommandLine line;
    private static final Logger log = LogManager.getLogger(Main.class.getName());

    void validate(String[] args, AAAService aaaService) throws ParseException {
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
        lineOptions(options, aaaService, userInput);
    }

    private void setLine(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();
        try {
            line = parser.parse(options, args);
            log.info("Параметры консоли спарсены");
        } catch (Exception e) {
            printHelp(options);
            log.error("Параметры консоли не парсятся", e.getMessage());
        }
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("gl_proj", options);
        log.info("Печать справки");
        System.exit(0);
    }


    private boolean isAccounting(AAAService aaaService, UserInput userInput) {
        userInput.vol = line.getOptionValue("vol");
        userInput.ds = line.getOptionValue("ds");
        userInput.de = line.getOptionValue("de");

        return aaaService.addAccount(userInput);
    }

    private boolean isAuthorisation(AAAService aaaService, UserInput userInput) {
        DbContext dbContext = new DbContext();
        userInput.res = line.getOptionValue("res");

        try {
            userInput.role = Role.valueOf(line.getOptionValue("role"));
        } catch (Exception e) {
            System.exit(3);
        }
        return (dbContext.getResourceFromBase(userInput) != null);
    }

    private boolean isAuthentication(AAAService aaaService, UserInput userInput) {
        userInput.login = line.getOptionValue("login");
        aaaService.findUser(userInput);
        if (line.hasOption("pass")) {
            userInput.pass = line.getOptionValue("pass");
            return aaaService.checkPass(userInput);
        }
        return false;
    }

    private void lineOptions(Options options, AAAService aaaService, UserInput userInput) {

        boolean authentication = false;
        boolean authorisation = false;
        if (line.hasOption("h") || line.getOptions().length == 0) {
            printHelp(options);
        }
        if (line.hasOption("login")) {
            authentication = isAuthentication(aaaService, userInput);
            log.info("Этап аутентификации прошел");
        }
        if (line.hasOption("res") && line.hasOption("role") && authentication) {
            authorisation = isAuthorisation(aaaService, userInput);
            log.info("Этап авторизации прошел");
        }
        if (line.hasOption("ds") && line.hasOption("de") && line.hasOption("vol") && (authorisation)) {
            isAccounting(aaaService, userInput);
            log.info("Этап аккаунтинга прошел");
        }
    }
}