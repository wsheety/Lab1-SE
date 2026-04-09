public class User {
    private String username;
    private String password;

    public User(String username, String password) throws Exception {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
    }

    private void validateUsername(String username) throws Exception {
        // Check length first
        if (username.length() > 50) {
            throw new Exception("Username is too long, try something shorter");
        }
        // Check email format: local@domain.tld
        // Part 1 (local): letters, digits, . _ % + -
        // Part 2 (domain): starts with letter/digit, allows letters, digits, . -
        // Part 3 (tld): at least 2 letters
        String emailRegex = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9][a-zA-Z0-9.\\-]*\\.[a-zA-Z]{2,}$";
        if (!username.matches(emailRegex)) {
            throw new Exception("Please enter a valid Email as username");
        }
    }

    private void validatePassword(String password) throws Exception {
        // Check length
        if (password.length() < 8) {
            throw new Exception("Your password is too short, add more characters");
        }
        if (password.length() > 12) {
            throw new Exception("Your password is too long, try a shorter one");
        }

        // Allowed special symbols: all symbols on the number row of the keyboard
        // ` ~ ! @ # $ % ^ & * ( ) - _ = +
        String allowedSymbols = "`~!@#$%^&*()-_=+";

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (allowedSymbols.indexOf(c) >= 0) {
                hasSymbol = true;
            } else {
                // Invalid character found
                throw new Exception("Please enter a valid password");
            }
        }

        // Must contain at least one letter, one digit, and one symbol
        if (!hasLetter || !hasDigit || !hasSymbol) {
            throw new Exception("Please enter a valid password");
        }
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username + " " + password;
    }
}
