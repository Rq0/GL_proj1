class Resource {
    String path;
    Role role;
    User user;
    Integer resourceId;

    Resource(int inputResourceId, String path, User user, Role role) {
        this.resourceId = inputResourceId;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
