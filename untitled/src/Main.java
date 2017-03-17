
import org.apache.commons.cli.ParseException;


public class Main {

    public static void main(String args[]) {
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

        try {
            validator.validate(args, aaaService);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println(aaaService.getAccounts());
        }
    }
}


