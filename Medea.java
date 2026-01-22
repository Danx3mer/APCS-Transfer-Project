import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Scanner;

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

            Scanner scanner = new Scanner(this.filePath);

            while(scanner.hasNextLine()) {  // Means there are more users to be read
                inUsers.add(new User()); //TODO: ACTUALLY CREATE USERS BASED ON THE INFILE
            }

            scanner.close();
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
        System.out.print("WELCOME TO THE MEDEA SCHOOL INTERFACE! PLEASE LOG IN WITH YOUR USERID BELOW, OR EXIT BY TYPING \":wq\":\n>>> ");
        Scanner scanner = new Scanner(System.in);
        
        switch(scanner.next()) {
            case ":wq": {
                try {
                    medeaIO.writeUsers(users);
                    System.out.print("\nFILE HAS BEEN WRITTEN TO SUCCESSFULLY!");
                } catch(Exception ex) { 
                    System.out.println("AN ERROR OCCURED WHILE WRITING TO THE FILE...\nERROR: " + ex.getMessage());
                }

                System.out.println("GOODBYE!");
            }
        }

        scanner.close();
    }
}