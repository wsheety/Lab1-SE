package org.openjfx.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Loads users from a classpath resource and filters to valid ones.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class UserRepository {

    /**
     * Reads users.txt from the classpath, building an ArrayList of valid Users.
     * Lines failing User validation (bad email, bad password) are silently skipped,
     * matching Lab 2 requirement 2.1.
     */
    public static ArrayList<User> loadUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();

        InputStream in = UserRepository.class.getResourceAsStream("users.txt");
        if (in == null) {
            throw new IOException("users.txt not found on classpath");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 2) continue;

                try {
                    users.add(new User(parts[0], parts[1]));
                } catch (Exception ignore) {
                    // invalid user — skip per requirement 2.1
                }
            }
        }
        return users;
    }
}