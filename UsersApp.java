import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class UsersApp {
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

            // Split by one or more spaces
            String[] parts = trimmed.split("\\s+");

            if (parts.length < 2) {
                System.err.println(trimmed + " --- Error: missing username or password");
                continue;
            }

            String username = parts[0];
            String password = parts[1];

            try {
                User user = new User(username, password);
                users.add(user);
            } catch (Exception e) {
                System.err.println(trimmed + " --- " + e.getMessage());
            }
        }

        reader.close();

        // Sort by username
        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));

        // Print sorted valid users
        for (User user : users) {
            System.out.println(user);
        }
    }
}
