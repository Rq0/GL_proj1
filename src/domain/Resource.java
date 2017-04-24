package domain;

public class Resource {
    public Integer id;
    private String path;
    private Role role;
    private User user;

    Resource() {
    }

    public Resource(int id, String path, User user, Role role) {
        this.id = id;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
