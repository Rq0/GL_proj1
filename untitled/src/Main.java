
import org.apache.commons.cli.ParseException;


public class Main {

    public static void main(String args[]) {
        AAAService aaaService = new AAAService();
        System.out.println("Create completed");

        aaaService.AddUser(0, "jdoe", "sup3rpaZZ", aaaService.addSalt());
        aaaService.AddUser(1, "jrow", "Qweqrty12", aaaService.addSalt());
        //Вывод списка пользователей
        System.out.println(aaaService.GetUsers());

        aaaService.AddResource(0, "a", aaaService.GetUser(0), AAAService.Role.READ.ordinal());
        aaaService.AddResource(1,"a.b", aaaService.GetUser(0),AAAService.Role.WRITE.ordinal());
        aaaService.AddResource(2,"a.b.c", aaaService.GetUser(1),AAAService.Role.EXECUTE.ordinal());
        aaaService.AddResource(3,"a.bc", aaaService.GetUser(0),AAAService.Role.EXECUTE.ordinal());

        System.out.println(aaaService.GetResources());
        try {
            Validator.Validate(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(aaaService.GetAccounts());


    }
}


