package users;

public abstract class User {
    protected String uid;

    public String getUID() { return uid; }

    public abstract void dashboardLoop();

    public abstract String toString();
    protected abstract void parse(String userDetails);
}
