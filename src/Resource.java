class Resource {
    Integer id;
    String path;
    Role role;
    User user;

    Resource() {
    }
    Resource(int id, String path, User user, Role role) {
        this.id = id;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
