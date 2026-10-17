package registration.facade;

import registration.subsystem.DatabaseService;
import registration.subsystem.EmailDeliveryException;
import registration.subsystem.EmailVerifier;
import registration.subsystem.PasswordHasher;
import registration.subsystem.UserRecord;
import registration.subsystem.ValidationException;
import registration.subsystem.ValidationService;

public class RegistrationFacade {

    private final ValidationService validator;
    private final PasswordHasher hasher;
    private final DatabaseService database;
    private final EmailVerifier emailVerifier;

    public RegistrationFacade() {
        this(new ValidationService(), new PasswordHasher(),
                new DatabaseService(), new EmailVerifier());
    }

    public RegistrationFacade(ValidationService validator, PasswordHasher hasher,
                              DatabaseService database, EmailVerifier emailVerifier) {
        this.validator = validator;
        this.hasher = hasher;
        this.database = database;
        this.emailVerifier = emailVerifier;
    }


    public RegistrationResult registerUser(String username, String email, String password)
            throws RegistrationException {
        try {
            validator.validateUsername(username);
            validator.validateEmail(email);
            validator.validatePassword(password);
        } catch (ValidationException e) {
            throw new RegistrationException("Invalid input: " + e.getMessage(), e);
        }

        if (database.existsByEmail(email)) {
            throw new RegistrationException("Email is already registered: " + email);
        }

        String passwordHash = hasher.hash(password);
        database.save(new UserRecord(username, email, passwordHash, false));

        try {
            String token = emailVerifier.sendVerificationEmail(email);
            return new RegistrationResult(username, email, token);
        } catch (EmailDeliveryException e) {
            database.deleteByEmail(email); // rollback: no half-registered users
            throw new RegistrationException(
                    "Could not send verification email, registration was rolled back", e);
        }
    }

    public void confirmEmail(String email, String token) throws RegistrationException {
        if (!database.existsByEmail(email)) {
            throw new RegistrationException("No registration found for " + email);
        }
        if (!emailVerifier.verify(email, token)) {
            throw new RegistrationException("Verification token is wrong");
        }
        database.markVerified(email);
    }
}
