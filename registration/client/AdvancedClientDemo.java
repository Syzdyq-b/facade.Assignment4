package registration.client;

import registration.subsystem.PasswordHasher;
import registration.subsystem.ValidationException;
import registration.subsystem.ValidationService;

public class AdvancedClientDemo {

    public static void main(String[] args) throws ValidationException {
        ValidationService validator = new ValidationService();
        validator.validatePassword("Strong123");

        PasswordHasher hasher = new PasswordHasher();
        String stored = hasher.hash("Strong123");
        System.out.println("Password matches: " + hasher.matches("Strong123", stored));
    }
}
