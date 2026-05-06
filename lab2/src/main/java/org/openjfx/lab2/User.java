package org.openjfx.lab2;

/**
 * Represents a system user with a validated username (email) and password.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class User {

    private String username;
    private String password;

    /**
     * Constructs a new User only if both username and password are valid.
     */
    public User(String username, String password) throws Exception {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
    }

    /** Validates the username as a proper email (max 50 chars). */
    private void validateUsername(String username) throws Exception {
        if (username.length() > 50) {
            throw new Exception("Username is too long, try something shorter");
        }
        String emailRegex = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9][a-zA-Z0-9.\\-]*\\.[a-zA-Z]{2,}$";
        if (!username.matches(emailRegex)) {
            throw new Exception("Please enter a valid Email as username");
        }
    }

    /** Validates the password: 8-12 chars, must contain letter + digit + symbol. */
    private void validatePassword(String password) throws Exception {
        if (password.length() < 8) {
            throw new Exception("Your password is too short, add more characters");
        }
        if (password.length() > 12) {
            throw new Exception("Your password is too long, try a shorter one");
        }

        String allowedSymbols = "`~!@#$%^&*()-_=+";
        boolean hasLetter = false, hasDigit = false, hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (allowedSymbols.indexOf(c) >= 0) hasSymbol = true;
            else throw new Exception("Please enter a valid password");
        }

        if (!hasLetter || !hasDigit || !hasSymbol) {
            throw new Exception("Please enter a valid password");
        }
    }

    public String getName() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() { return username + " " + password; }
}