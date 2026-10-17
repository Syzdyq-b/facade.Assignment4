package registration.subsystem;

/** A row stored by DatabaseService. Immutable. */
public record UserRecord(String username, String email, String passwordHash, boolean verified) {

    public UserRecord asVerified() {
        return new UserRecord(username, email, passwordHash, true);
    }
}
