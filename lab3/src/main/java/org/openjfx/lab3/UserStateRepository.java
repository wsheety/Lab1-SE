package org.openjfx.lab3;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Holds a UserState for every valid user, keyed by email.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class UserStateRepository {

    private final ConcurrentMap<String, UserState> stateByEmail;

    public UserStateRepository(ArrayList<User> validUsers) {
        this.stateByEmail = new ConcurrentHashMap<>();
        for (User u : validUsers) {
            stateByEmail.put(u.getName(), new UserState(u));
        }
    }

    /** Returns the state for a known email, or null if the email isn't registered. */
    public UserState findByEmail(String email) {
        if (email == null) return null;
        return stateByEmail.get(email);
    }

    /** Returns the state if both email and password match a valid user, else null. */
    public UserState findMatching(String email, String password) {
        UserState state = findByEmail(email);
        if (state == null) return null;
        if (!state.getUser().getPassword().equals(password)) return null;
        return state;
    }
}
