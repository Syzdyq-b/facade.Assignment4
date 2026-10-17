package registration.client;

import registration.facade.RegistrationException;
import registration.facade.RegistrationFacade;
import registration.facade.RegistrationResult;

public class Main {

    public static void main(String[] args) {
        RegistrationFacade registration = new RegistrationFacade();

        System.out.println("1) Successful registration");
        RegistrationResult result = tryRegister(registration,
                "aigerim_k", "aigerim@mail.com", "Strong1234");

        System.out.println("\n2) Weak password");
        tryRegister(registration, "bauyrzhan", "bauka@mail.com", "weak");

        System.out.println("\n3) Duplicate email");
        tryRegister(registration, "another_user", "AIGERIM@mail.com", "Strong1231");

        System.out.println("\n4) Email delivery failure (rollback)");
        tryRegister(registration, "dana_s", "dana@unreachable.test", "Strong1232");
        System.out.println("   Retry with the same address is rejected by the mail server again,"
                + " not by 'already registered' -> user was really rolled back");
        tryRegister(registration, "dana_s", "dana@unreachable.test", "Strong1235");

        System.out.println("\n5) Confirm email");
        try {
            registration.confirmEmail("aigerim@mail.com", "wrong-token");
        } catch (RegistrationException e) {
            System.out.println("   FAILED: " + e.getMessage());
        }
        try {
            registration.confirmEmail(result.email(), result.demoVerificationToken());
            System.out.println("   OK: email confirmed");
        } catch (RegistrationException e) {
            System.out.println("   FAILED: " + e.getMessage());
        }
    }

    private static RegistrationResult tryRegister(RegistrationFacade facade,
                                                  String username, String email, String password) {
        try {
            RegistrationResult result = facade.registerUser(username, email, password);
            System.out.println("   OK: registered " + result.username());
            return result;
        } catch (RegistrationException e) {
            System.out.println("   FAILED: " + e.getMessage());
            return null;
        }
    }
}
