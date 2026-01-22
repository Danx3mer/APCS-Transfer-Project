import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

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

        /**
         * Reads the user data from the filepath specified in the constructor
         * @return The Parsed User Data
         */
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

        /**
         * Deletes the database file and writes the new data to a new file with the same path.
         * @param users the user data to write to the file
         */
        public void writeUsers(ArrayList<User> users) {
            try { 
                Files.deleteIfExists(Path.of(filePath));
            } catch(Exception e) {
                System.out.println("Couldn't clear database file + " + e.getMessage());
            }

            for(User user: users) {
                try { 
                    Files.writeString(Path.of(filePath), user.toString());
                } catch (Exception e) {
                    System.out.println("Couldn't write to the database file... + " + e.getMessage());
                }
            }
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

    public Student requestStudent(String authUID, String requestedUID) throws Exception {
        if(authUID.charAt(0) == 's') throw new IllegalAccessException("Tried to modify student as student!");

        for(User user: users) {
            if(user.getUID() == requestedUID) return (Student) user; //No need to check instanceof b/c UID starting with s guarantees student
        }
        
        throw new NameNotFoundException("requested id not found");
    }

    public Teacher requestTeacher(String authUID, String requestedUID) throws Exception {
        if(authUID.charAt(0) != 'a') throw new IllegalAccessException("Tried to modify teacher as non-admin!");

        for(User user: users) {
            if(user.getUID() == requestedUID) return (Teacher) user; //No need to check instanceof b/c UID starting with t guarantees teacher
        }
        
        throw new NameNotFoundException("requested id not found");
    }

    public Admin requestAdmin(String authUID, String requestedUID) throws Exception {
        if(authUID.charAt(0) != 't') throw new IllegalAccessException("Tried to access admin info as non-teacher!");

        for(User user: users) {
            if(user.getUID() == requestedUID) return (Admin) user; //No need to check instanceof b/c UID starting with a guarantees admin
        }
        
        throw new NameNotFoundException("requested id not found");
    }
}