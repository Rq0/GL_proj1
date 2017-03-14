class Resource {
    String path;
    Role role;
    User user;

    Resource(int inputResourceId, String path, User user, Role role) {
        Integer resourceId = inputResourceId;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
