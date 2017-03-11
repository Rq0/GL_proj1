/**
 * Класс разбирающий входные параметры
 * Created by rq0 on 11.03.2017.
 */

import org.apache.commons.cli.*;

class Validator {

    private static Options options;
    private static CommandLine line;
    private static CommandLineParser parser;

    static void Validate(String[] args) throws ParseException {
        AAAService aaaService = new AAAService();
        boolean authentication = false, authorisation = false, accounting = false;
        UserInput userInput = new UserInput();
        parser = new GnuParser();

        //добавление всех возможных параметров
        options = new Options();
        options.addOption("login", "login", true, "Логин пользователя");
        options.addOption("pass", "password", true, "Пароль пользователя");
        options.addOption("role", "role", true, "Роль пользователя на выбранном ресурсе");
        options.addOption("res", "resource", true, "Адрес ресурса");
        options.addOption("ds", "DateStart", true, "Дата начала");
        options.addOption("de", "DateEnd", true, "Дата окончания");
        options.addOption("vol", "volume", true, "Объем");
        options.addOption("h", "help", false, "Cправка");

        //получение входных параметров
        line = parser.parse(options, args);
        //Костылина на запуск без параметров или с неизвестными параметрами
        boolean NoParams = (!line.hasOption("login") && !line.hasOption("password") &&
                !line.hasOption("role") && !line.hasOption("resource") &&
                !line.hasOption("DateStart") && !line.hasOption("DateEnd") && !line.hasOption("vol"));

        if (line.hasOption("h") || NoParams) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("gl", options);
            System.exit(0);
        }


        if (line.hasOption("login")) {
            userInput.login = line.getOptionValue("login");
            aaaService.FindUser(userInput);

            if (line.hasOption("pass")) {
                userInput.pass = line.getOptionValue("pass");
                authentication = aaaService.CheckPass(userInput);
            }
        }

        if (authentication) {
            System.out.println("Authentication complete");
        }

        if (line.hasOption("res") && line.hasOption("role") && authentication) {
            userInput.res = line.getOptionValue("res");
            //костыли с ролью(отменяются, придумать как заменить на свич)

            if (AAAService.Role.EXECUTE.toString().equals(line.getOptionValue("role"))) {
                userInput.role = "2";
            } else if (AAAService.Role.WRITE.toString().equals(line.getOptionValue("role"))) {
                userInput.role = "1";
            } else if (AAAService.Role.READ.toString().equals(line.getOptionValue("role"))) {
                userInput.role = "0";
            } else {
                System.exit(3);
            }
            authorisation = aaaService.CheckRole(userInput);
            if (authorisation) {
                System.out.println("Authorisation complete");
            }
        }

        if (line.hasOption("ds") && line.hasOption("de") && line.hasOption("vol") && (authorisation)) {
            userInput.vol = line.getOptionValue("vol");
            userInput.ds = line.getOptionValue("ds");
            userInput.de = line.getOptionValue("de");

            accounting = aaaService.AddAccount(userInput);

            if (accounting) {
                System.out.println("Accounting complete");
            }

        }
    }
}
