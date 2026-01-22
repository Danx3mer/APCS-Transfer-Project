package users;

public final class Student extends User {

    public Student(String studentDetails) {
        this.parse(studentDetails);
    }

    @Override
    public void dashboardLoop() {
        System.out.println("This is an admin panel, not a student panel!");
    }

    @Override
    public String toString() {
        return this.getUID();
    }

    @Override
    protected void parse(String userDetails) {
        this.uid = userDetails;
    }
}
