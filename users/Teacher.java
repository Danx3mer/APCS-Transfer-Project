package users;

public final class Teacher extends User {
    private String className;

    public Teacher(String teacherDetails) {
        this.parse(teacherDetails);
    }

    public Teacher(String id, String className) {
        this.uid = id;
        this.className = className;        
    }

    @Override
    public void dashboardLoop() {
        
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dashboardLoop'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    @Override
    protected void parse(String userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }
    
}
