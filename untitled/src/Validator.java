import org.apache.commons.cli.*;

class Validator {

    private CommandLine line;

    private boolean authentication = false;
    private boolean authorisation = false;
    private UserInput userInput = new UserInput();

    void validate(String[] args, AAAService aaaService) throws ParseException {

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

        if (line.hasOption("h") || line.getOptions().length == 0) {
            printHelp(options);
        }
        if (line.hasOption("login")) {
            checkLoginPass(aaaService);
        }
        if (line.hasOption("res") && line.hasOption("role") && authentication) {
            checkResRole(aaaService);
        }
        if (line.hasOption("ds") && line.hasOption("de") && line.hasOption("vol") && (authorisation)) {
            checkDsDeVol(aaaService);
        }

    }

    private void setLine(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();
        try {
            line = parser.parse(options, args);
        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("gl", options);
            System.exit(0);
        }
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("gl", options);
        System.exit(0);
    }

    private void checkLoginPass(AAAService aaaService) {
        userInput.login = line.getOptionValue("login");
        aaaService.findUser(userInput);
        if (line.hasOption("pass")) {
            userInput.pass = line.getOptionValue("pass");
            authentication = aaaService.checkPass(userInput);
            if (authentication) {
                System.out.println("Authentication complete");
            }
        }
    }


    private void checkResRole(AAAService aaaService) {

        userInput.res = line.getOptionValue("res");

        try {
            userInput.role = Role.valueOf(line.getOptionValue("role"));
        } catch (Exception e) {
            System.exit(3);
        }
        authorisation = aaaService.checkRole(userInput);
        if (authorisation) {
            System.out.println("Authorisation complete");
        }

    }

    private void checkDsDeVol(AAAService aaaService) {

        userInput.vol = line.getOptionValue("vol");
        userInput.ds = line.getOptionValue("ds");
        userInput.de = line.getOptionValue("de");

        boolean accounting = aaaService.addAccount(userInput);
        if (accounting) {
            System.out.println("Accounting complete");
        }
    }
}