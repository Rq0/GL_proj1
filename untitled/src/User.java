class User {
    String login;
    String pass;
    String salt;
    int id;

    User(int id, String login, String pass, String salt) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.salt = salt;
    }
}
