import main.java.services.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;


public class Main {
    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String args[]) {
        log.info("-------------------------------------------------------------");
        log.info("Приложение запущено");
        Validator validator = new Validator();

        migration();

        validator.validate(args);

        log.info("Приложение закрыто");
    }

    private static void migration() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:./GL_proj1", "sa", "");
        log.info("DataSource для миграции установлен");
        try {
            flyway.migrate();
            log.info("Миграция успешна");
        } catch (FlywayException e) {
            flyway.baseline();
            log.warn("Миграция не прошла");
        }
    }

}


