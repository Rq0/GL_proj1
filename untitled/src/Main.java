/**
 * Created by rq0 on 06.03.2017.
 */
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.cli.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import static java.lang.System.exit;
import org.apache.commons.cli.*;



public class Main {
    private static Options options;
    private static CommandLine line;
    private static ArrayList <User>Users;
    private static CommandLineParser parser;
    public static void main(String args[]) {
        try {
            Validator(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Create completed");
        System.out.println(args);
        System.exit(0);
        User First = new User(1,"FirstLogin","FirstPass");
        User Sec = new User(2,"SecLogin","SecPass");
        System.exit(1);
        Users.add(First);
        Users.add(Sec);
        GetUser(Users);

    }

    private static void Validator(String[] args) throws ParseException {
        CommandLineParser parser = new GnuParser();

        options = new Options();
        options.addOption("login", "login", true, "Логин пользователя");
        options.addOption("pass", "password", true, "Пароль пользователя");
        options.addOption("role", "role", true, "Роль пользователя на выбранном ресурсе");
        options.addOption("res", "resource", true, "Адрес ресурса");
        options.addOption("ds", "DateStart", true, "Дата начала");
        options.addOption("de", "DateEnd", true, "Дата окончания");
        options.addOption("val", "value", true, "Объем");
        options.addOption("h", "help", false, "Cправка");
        line = parser.parse(options, args);

        if(line.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("gl", options);
        }
        Users = new ArrayList();

        if(line.hasOption("-login") && line.hasOption("-pass")) {
            User NewUser = new User(1, "FirstLogin", "FirstPass");
            Users.add(NewUser);
        }
    }

    private static void GetUser(ArrayList<User> users) {
        for (int i = 0; i < 2; i++) {
            System.out.println(users.get(i).login);
        }
        for (int i = 0; i < 2; i++) {
            System.out.println(users.get(i).pass);
        }
    }
}
