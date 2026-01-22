package users;

import java.util.Scanner;

import medea.Medea;

public final class Admin extends User {
    private boolean reportCardsEditable;

    public Admin(String admDetails) {
        this.parse(admDetails);
    }

    public boolean getReportCardStatus() {
        return this.reportCardsEditable;
    }

    @Override
    public void dashboardLoop() {
        while (true) {
            final String dashboardMsg = "\n\n[1] Add Student\n[2] Add Teacher\n[3] Change report cards to be editable or send them out\n[4] Exit";
            System.out.println(dashboardMsg);

            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextInt()) {
                case 1: {
                    System.out.println("What is the student's id? (Must begin w/ an \"s\")");
                    boolean pass = false;
                    do {
                        String id = scanner.next();
                        if (id.charAt(0) != 's')
                            System.out.println("Student's id must begin with an \"s\"!");
                        else {
                            Medea medea = Medea.getInstance();
                            medea.createUser(this.getUID(), new Student(id));
                            pass = true;
                        }
                    } while (!pass);
                }
                    break;
                case 2: {
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
                    Medea medea = Medea.getInstance();
                    medea.createUser(this.getUID(), new Teacher(id, scanner.next()));

                    System.out.println("Teacher successfully added to roster!");
                }
                    break;
                case 3: {
                    System.out.println("Report cards are currently" +
                            (!this.reportCardsEditable ? " NOT " : " ") +
                            "editable. Would you like to toggle this option? (y/n)");
                    boolean pass = false;
                    do {
                        String inLine = scanner.nextLine().toLowerCase();

                        if (inLine.startsWith("y")) {
                            this.reportCardsEditable = !this.reportCardsEditable;
                            System.out.println("Done! Report cards are now" + (!this.reportCardsEditable ? " NOT " : " ")
                                    + "editable!");
                            pass = true;
                        } else if (inLine.startsWith("n")) {
                            System.out.println("Nothing changed, returning to dashboard");
                            pass = true;
                        } else {
                            System.out.println("Please start your answer with a y or an n");
                            pass = false;
                        }
                    } while (!pass);
                }
                break;
                case 4: {
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
        return this.uid.concat("!!".concat(Boolean.toString(this.reportCardsEditable)));
    }

    @Override
    protected void parse(String userDetails) {
        int sep = userDetails.indexOf("!!");
        this.uid = userDetails.substring(0, sep);
        this.reportCardsEditable = Boolean.valueOf(userDetails.substring(sep + 2));
    }
}
