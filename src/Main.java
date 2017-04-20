import org.apache.commons.cli.ParseException;
import org.flywaydb.core.Flyway;


public class Main {
    //  private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String args[]) {
        //  log.info("Приложение запущено");
        AAAService aaaService;
        aaaService = new AAAService();
        Validator validator;
        validator = new Validator();


        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:tcp://localhost/~/test", "sa", "");
        try {
            flyway.migrate();
        } catch (Exception e) {
            flyway.baseline();
        }

        System.out.println(aaaService.getUsers());
        System.out.println(aaaService.getResources());

        /*
          Подавляю исключение здесь, потому что возможные исключения обрабатывается внутри класса Validator
         */
        try {
            validator.validate(args, aaaService);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            System.out.println(aaaService.getAccounts());
        }
        //     log.info("Приложение закрыто");
    }

}


