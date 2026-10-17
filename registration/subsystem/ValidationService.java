package registration.subsystem;

import java.util.regex.Pattern;

/** Subsystem class 1: checks username, email and password rules. */
public class ValidationService {

    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_]{3,20}$");
    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    public void validateUsername(String username) throws ValidationException {
        if (username == null || !USERNAME.matcher(username).matches()) {
            throw new ValidationException(
                    "Username must be 3-20 characters: letters, digits or underscore");
        }
    }

    public void validateEmail(String email) throws ValidationException {
        if (email == null || !EMAIL.matcher(email).matches()) {
            throw new ValidationException("Email format is invalid");
        }
    }

    public void validatePassword(String password) throws ValidationException {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long");
        }
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        if (!hasDigit || !hasUpper) {
            throw new ValidationException(
                    "Password must contain at least one digit and one uppercase letter");
        }
    }
}
