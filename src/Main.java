
import org.apache.commons.cli.ParseException;

import java.sql.*;


public class Main {

    public static void main(String args[]) {
        db();
        AAAService aaaService;
        aaaService = new AAAService();
        Validator validator;
        validator = new Validator();

        aaaService.addUser(0, "jdoe", "sup3rpaZZ");
        aaaService.addUser(1, "jrow", "Qweqrty12");
        System.out.println(aaaService.getUsers());

        aaaService.addResource(0, "a", aaaService.getUser(0), Role.READ);
        aaaService.addResource(1, "a.b", aaaService.getUser(0), Role.WRITE);
        aaaService.addResource(2, "a.b.c", aaaService.getUser(1), Role.EXECUTE);
        aaaService.addResource(3, "a.bc", aaaService.getUser(0), Role.EXECUTE);
        System.out.println(aaaService.getResources());

        /**
         * Подавляю исключение здесь, потому что возможные исключения обрабатывается внутри класса Validator
         */
        try {
            validator.validate(args, aaaService);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            System.out.println(aaaService.getAccounts());
        }
    }

    static void db() {
        try {
            Class.forName("org.h2.Driver");
            //:tcp://localhost/~/test
            Connection conn = DriverManager.getConnection("jdbc:h2:./GL_proj1",
                    "sa", "");
            Statement st = null;
            st = conn.createStatement();
            st.execute("create table IF NOT EXISTS user(id int primary key, name varchar(255))");
            st.execute("insert into user values(3, 'Hello')");
            ResultSet result;
            result = st.executeQuery("SELECT * FROM User");
            while (result.next()) {
                String name = result.getString("NAME");
                System.out.println(result.getString("ID") + " " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


