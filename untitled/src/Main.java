
import org.apache.commons.cli.ParseException;


public class Main {

    public static void main(String args[]) {
        AAAService aaaService = new AAAService();

        aaaService.AddUser(0, "jdoe", "sup3rpaZZ");
        aaaService.AddUser(1, "jrow", "Qweqrty12");

        aaaService.AddResource(0, "a", aaaService.GetUser(0), Role.READ);
        aaaService.AddResource(1, "a.b", aaaService.GetUser(0), Role.WRITE);
        aaaService.AddResource(2, "a.b.c", aaaService.GetUser(1), Role.EXECUTE);
        aaaService.AddResource(3, "a.bc", aaaService.GetUser(0), Role.EXECUTE);

        try {
            Validator.Validate(args, aaaService);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}


