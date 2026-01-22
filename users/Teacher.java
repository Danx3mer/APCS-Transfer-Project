package users;

import java.util.ArrayList;
import java.util.Scanner;

import medea.Medea;

public final class Teacher extends User {
    private String className;
    private ArrayList<String> studentUIDs;

    public Teacher(String teacherDetails) {
        this.studentUIDs = new ArrayList<String>();
        this.parse(teacherDetails);
    }

    public Teacher(String id, String className) {
        this.uid = id;
        this.className = className;
        this.studentUIDs = new ArrayList<String>();
    }

    @Override
    public void dashboardLoop() {
        while (true) {
            Medea medea = Medea.getInstance();

            final String dashboardMsg = "\n[1] Add Student\n[2] Exit";
            System.out.println(dashboardMsg);

            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextInt()) {
                case 1: {
                    System.out.println("What is the student's id? (Must begin w/ a \"s\")");
                    String id;
                    boolean pass = false;
                    do {
                        id = scanner.next();
                    
                        if(medea.checkValid(id) && id.charAt(0) == 's') {
                            pass = true;
                        }
                        else System.out.println("This student doesn't exist");
                    } while (!pass);

                    this.studentUIDs.add(id);

                    System.out.println("Added!");
                }
                    break;
                case 2: {
                    System.out.println("Logging out...");
                    return;
                }
                default: {
                    System.out.println("Not a valid choice, please select from the choices below:\n" + dashboardMsg);
                }
            }
        }
    }

    @Override
    public String toString() {
        String res = this.getUID().concat("!")
                .concat(this.className).concat("!!");

        if(this.studentUIDs!=null)                
        for(String sid: this.studentUIDs) {
            res = res.concat(sid).concat("+");
        }

        return res;
    }

    @Override
    protected void parse(String userDetails) {
        int sep1 = userDetails.indexOf("!");
        int sep2 = userDetails.indexOf("!!");
        this.uid = userDetails.substring(0, sep1);
        this.className = userDetails.substring(sep1 + 1, sep2);

        String SIDs = userDetails.substring(sep2 + 2);
        while(SIDs.contains("+")) {
            this.studentUIDs.add(SIDs.substring(0, SIDs.indexOf('+')));
            SIDs = SIDs.substring(SIDs.indexOf('+') + 1);
        }
    }   
}
