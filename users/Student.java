package users;

import java.util.HashMap;
import java.util.Scanner;

import medea.Medea;

public final class Student extends User {
    private HashMap<String, Double> grades;

    public Student(String studentDetails) {
        this.parse(studentDetails);
        this.grades = new HashMap<String, Double>();
    }

    @Override
    public void dashboardLoop() {
        System.out.println("Welcome student! Here are your grades (type anything and press enter to logout):");
        if(grades != null)
        grades.forEach((classN, value) -> {
            System.out.println(classN.concat(": ").concat(String.valueOf(value)));
        });
        else System.out.println("N/A");

        Scanner scanner = new Scanner(System.in);
        scanner.next(); //Wait for next keypress before continuing back to login screen
    }

    @Override
    public String toString() {
        String result = this.getUID();

        if(grades!=null)
        grades.forEach((classN, grade) -> {
            result.concat("!").concat(classN)
                    .concat("!!").concat(String.valueOf(grade));
        });

        return result;
    }

    @Override
    protected void parse(String userDetails) {
        int sep = userDetails.indexOf('!');
        if(sep==-1) {
            this.uid = userDetails;
            return;
        }

        this.uid = userDetails.substring(0, sep);
        String gr = userDetails.substring(sep + 1);
        while(gr.contains("!")) {
            grades.put(gr.substring(0, gr.indexOf('!')), 
                Double.valueOf(gr.substring(gr.indexOf('!'), gr.indexOf("!!"))));
        }
    }
    
    /**
     * Modifies student grade
     * @param authID    authorizer of grade change
     * @param className name of class
     * @param grade     grade value
     * @return          whether or not the grade change was successful
     */
    public boolean modifyClassData(String authID, String className, double grade) {
        Medea medea = Medea.getInstance();

        if(!medea.checkValid(authID)) return false;

        this.grades.put(className, grade);
        
        return true;
    }
}
