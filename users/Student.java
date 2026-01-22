package users;

import medea.Medea;

public final class Student extends User {

    public Student(String studentDetails) {
        this.parse(studentDetails);
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
    
    public boolean modifyClassData(String authID, String className, double grade) {
        Medea medea = Medea.getInstance();

        if(!medea.checkValid(authID)) return false;
        
        return true;
    }
}
