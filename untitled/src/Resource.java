class Resource {
    String path;
    Role role;
    User user;
    private int resourceID;

    Resource(int resourceID, String path, User user, Role role) {
        this.resourceID = resourceID;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
