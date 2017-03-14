import org.apache.commons.cli.*;

class Validator {

    private static CommandLine line;

    static void validate(String[] args, AAAService aaaService) throws ParseException {
        boolean authentication = false;
        boolean authorisation = false;
        boolean accounting;
        UserInput userInput = new UserInput();
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("login", "login", true, "Логин пользователя");
        options.addOption("pass", "password", true, "Пароль пользователя");
        options.addOption("role", "role", true, "Роль пользователя на выбранном ресурсе");
        options.addOption("res", "resource", true, "Адрес ресурса");
        options.addOption("ds", "DateStart", true, "Дата начала");
        options.addOption("de", "DateEnd", true, "Дата окончания");
        options.addOption("vol", "volume", true, "Объем");
        options.addOption("h", "help", false, "Cправка");

        try {
            line = parser.parse(options, args);
        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("gl", options);
            System.exit(0);
        }

        if (line.hasOption("h") || line.getOptions().length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("gl", options);
            System.exit(0);
        }


        if (line.hasOption("login")) {
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



        if (line.hasOption("res") && line.hasOption("role") && authentication) {
            userInput.res = line.getOptionValue("res");

            try{
                userInput.role = Role.valueOf(line.getOptionValue("role"));
            }
            catch (Exception e){
                System.exit(3);
            }
            authorisation = aaaService.checkRole(userInput);
            if (authorisation) {
                System.out.println("Authorisation complete");
            }
        }

        if (line.hasOption("ds") && line.hasOption("de") && line.hasOption("vol") && (authorisation)) {
            userInput.vol = line.getOptionValue("vol");
            userInput.ds = line.getOptionValue("ds");
            userInput.de = line.getOptionValue("de");

            accounting = aaaService.addAccount(userInput);
            if (accounting) {
                System.out.println("Accounting complete");
            }
        }
    }
}