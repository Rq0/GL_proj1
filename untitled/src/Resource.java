class Resource {
    String path;
    Integer role;
    User user;
    private int resourceID;

    Resource(int resourceID, String path, User user, int role) {
        this.resourceID = resourceID;
        this.path = path;
        this.user = user;
        this.role = role;
    }
}
