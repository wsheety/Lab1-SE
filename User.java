/**
 * Represents a system user with a validated username (email) and password.
 * Follows OOP principles: encapsulation and information hiding.
 *
 * @authors Waseem Sheety, Adam Karain
 */
public class User {

    private String username;
    private String password;

    /**
     * Constructs a new User only if both username and password are valid.
     *
     * @param username the user's email address
     * @param password the user's password
     * @throws Exception if the username or password is invalid, with a descriptive message
     */
    public User(String username, String password) throws Exception {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
    }

    /**
     * Validates the username as a proper email address (max 50 characters).
     * Email format: local@domain.tld
     *   - Part 1 (local): letters, digits, and the characters . _ % + -
     *   - Part 2 (domain): must start with a letter or digit, allows letters, digits, . and -
     *   - Part 3 (tld): at least 2 letters
     *
     * @param username the username to validate
     * @throws Exception if the username is too long or not a valid email format
     */
    private void validateUsername(String username) throws Exception {
        // Check length before format
        if (username.length() > 50) {
            throw new Exception("Username is too long, try something shorter");
        }
        String emailRegex = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9][a-zA-Z0-9.\\-]*\\.[a-zA-Z]{2,}$";
        if (!username.matches(emailRegex)) {
            throw new Exception("Please enter a valid Email as username");
        }
    }

    /**
     * Validates the password according to the following rules:
     *   - Length: 8 to 12 characters
     *   - Allowed characters: letters (upper/lower), digits, and symbols from the keyboard number row:
     *     ` ~ ! @ # $ % ^ & * ( ) - _ = +
     *   - Must contain at least one letter, one digit, and one symbol
     *
     * @param password the password to validate
     * @throws Exception if the password fails any validation rule
     */
    private void validatePassword(String password) throws Exception {
        // Check length bounds
        if (password.length() < 8) {
            throw new Exception("Your password is too short, add more characters");
        }
        if (password.length() > 12) {
            throw new Exception("Your password is too long, try a shorter one");
        }

        // All symbols from the number row of the keyboard
        String allowedSymbols = "`~!@#$%^&*()-_=+";

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        // Check each character is valid and track required types
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (allowedSymbols.indexOf(c) >= 0) {
                hasSymbol = true;
            } else {
                // Character is not a letter, digit, or allowed symbol
                throw new Exception("Please enter a valid password");
            }
        }

        // Must contain at least one of each required type
        if (!hasLetter || !hasDigit || !hasSymbol) {
            throw new Exception("Please enter a valid password");
        }
    }

    /**
     * Returns the username.
     *
     * @return the username (email address)
     */
    public String getName() {
        return username;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns a string representation of the user in the format: "username password"
     *
     * @return formatted string with username and password separated by a space
     */
    @Override
    public String toString() {
        return username + " " + password;
    }
}
