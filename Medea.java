import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Scanner;

import users.Admin;
import users.Student;
import users.Teacher;
import users.User;

public class Medea {
    private MedeaIO medeaIO;
    private ArrayList<User> users;

    private class MedeaIO {
        private String filePath;

        public MedeaIO(String path) {
            this.filePath = path;
        }

        public ArrayList<User> readUsers() {
            ArrayList<User> inUsers = new ArrayList<User>();

            try {
                Scanner scanner = new Scanner(new File(this.filePath));
                while (scanner.hasNextLine()) { // Means there are more users to be read
                    String line = scanner.nextLine();
                    System.out.println(line);
                    switch (line.charAt(0)) {
                        case 'a': {
                            inUsers.add(new Admin(line));
                        }
                            break;
                        case 't': {
                            inUsers.add(new Teacher(line));
                        }
                            break;
                        case 's': {
                            inUsers.add(new Student(line));
                        }
                            break;
                        default: {
                            throw new UncheckedIOException(
                                    new IOException("INVALID USER TYPE FOUND WHILE READING FILE!"));
                        }
                    }
                }
                scanner.close();

            } catch (FileNotFoundException e) { // File doesn't exist!
                inUsers.add(new Admin("aadmin")); // Add the default admin account
            }

            return inUsers;
        }

        public void writeUsers(ArrayList<User> users) {

        }
    }

    public Medea(String pathToFile) {
        medeaIO = new MedeaIO(pathToFile);
        users = medeaIO.readUsers();
        this.mainLoop();
    }

    public void mainLoop() {
        System.out.print("WELCOME TO THE MEDEA INTERFACE! PLEASE LOG IN WITH YOUR USERID BELOW, OR EXIT BY TYPING \":wq\":\n>>> ");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.next()) {
            case ":wq": {
                try {
                    medeaIO.writeUsers(users);
                    System.out.println("\nFILE HAS BEEN WRITTEN TO SUCCESSFULLY!");
                } catch (Exception ex) {
                    System.out.println("AN ERROR OCCURED WHILE WRITING TO THE FILE...\nERROR: " + ex.getMessage());
                }

                System.out.print("\nGOODBYE!\n");
            }
        }

        scanner.close();
    }
}