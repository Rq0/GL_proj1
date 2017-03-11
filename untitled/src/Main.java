/**
 * Главный класс программы
 * Created by rq0 on 06.03.2017.
 */

import org.apache.commons.cli.ParseException;


public class Main {

    public static void main(String args[]) {
        AAAService aaaService = new AAAService();
        System.out.println("Create completed");


        aaaService.AddUser(0, "FirstLogin", "FirstPas", aaaService.addSalt());
        aaaService.AddUser(1, "SecLogin", "SecPass", aaaService.addSalt());
        //Вывод списка пользователей
        System.out.println(aaaService.GetUsers());

        aaaService.AddResource(0, "AA.BB", aaaService.GetUser(0), AAAService.Role.READ.ordinal());
        System.out.println(aaaService.GetResources());
        try {
            Validator.Validate(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(aaaService.GetAccounts());


    }
}


