import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;


public class Main {
      private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String args[]) {
        log.info("-------------------------------------------------------------");
        log.info("Приложение запущено");
        AAAService aaaService;
        aaaService = new AAAService();
        Validator validator;
        validator = new Validator();

        migration();

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
        log.info("Приложение закрыто");
    }

    private static void migration() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:tcp://localhost/~/test", "sa", "");
        log.info("DataSource для миграции установлен");
        try {
            flyway.migrate();
            log.info("Миграция успешна");
        } catch (FlywayException e) {
            flyway.baseline();
            log.warn("Миграция не прошла {}", e.getMessage());
        }
    }

}


