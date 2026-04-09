import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Main application class for reading and validating system users from a file.
 * Reads users.txt line by line, validates each entry, and prints valid users sorted by username.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class UsersApp {

    /**
     * Entry point of the application.
     * Reads users.txt, attempts to create a User for each line,
     * prints errors for invalid entries, and prints valid users sorted alphabetically.
     *
     * @param args command-line arguments (not used)
     * @throws FileNotFoundException if users.txt is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        File readFile = new File("users.txt");
        Scanner reader = new Scanner(readFile);
        ArrayList<User> users = new ArrayList<>();

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String trimmed = line.trim();

            // Skip empty lines
            if (trimmed.isEmpty()) {
                continue;
            }

            // Split by one or more spaces to get username and password tokens
            String[] parts = trimmed.split("\\s+");

            if (parts.length < 2) {
                System.err.println(trimmed + " --- Error: missing username or password");
                continue;
            }

            String username = parts[0];
            String password = parts[1];

            // Try to create a User — constructor throws if data is invalid
            try {
                User user = new User(username, password);
                users.add(user);
            } catch (Exception e) {
                // Print the invalid line along with the reason it failed
                System.err.println(trimmed + " --- " + e.getMessage());
            }
        }

        reader.close();

        // Sort valid users alphabetically by username
        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));

        // Print each valid user in "username password" format
        for (User user : users) {
            System.out.println(user);
        }
    }
}
