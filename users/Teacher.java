package users;

import java.util.ArrayList;
import java.util.Scanner;

import medea.Medea;

public final class Teacher extends User {
    private String className;
    private ArrayList<String> studentUIDs;

    public Teacher(String teacherDetails) {
        this.parse(teacherDetails);
    }

    public Teacher(String id, String className) {
        this.uid = id;
        this.className = className;        
    }

    @Override
    public void dashboardLoop() {
        while (true) {
            Medea medea = Medea.getInstance();

            final String dashboardMsg = "\n\n[1] Add Student\n[2] Grade Student\n[3] Exit";
            System.out.println(dashboardMsg);

            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextInt()) {
                case 2: {
                    System.out.println("What is the student's id? (Must begin w/ an \"s\")");
                    boolean pass = false;
                    Student student;
                    do {
                        try {
                            student = medea.requestStudent(this.getUID(), scanner.next());
                            
                            System.out.println("What is the grade you would like to give this student?");
                            
                            student.modifyClassData(this.uid, this.className, scanner.nextDouble());
                        } catch (Exception e) {
                            if(e instanceof IllegalArgumentException) {
                                System.out.println("Please enter in a valid student ID! (Must begin w/ an \"s\")");
                            }
                        } finally {
                            pass = true;
                        }
                    } while (!pass);

                }
                    break;
                case 1: {
                    System.out.println("What is the teacher's id? (Must begin w/ a \"t\")");
                    String id;
                    boolean pass = false;
                    do {
                        id = scanner.next();
                        if (id.charAt(0) != 't')
                            System.out.println("Teacher's id must begin with a \"t\"!");
                        else
                            pass = true;
                    } while (!pass);

                    System.out.println("What is the teacher's class' name?");
                    Medea.getInstance().createUser(this.getUID(), new Teacher(id, scanner.next()));
                }
                    break;
                case 3: {
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
            res.concat(sid).concat("+");
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
