package users;

public abstract class User {
    private String uid;

    public String getUID() { return this.uid; }
    
    public abstract String dashboardLoop();

    public abstract String toString();
    protected abstract void parse(String userDetails);
}
