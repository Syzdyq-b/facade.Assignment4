package registration.subsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DatabaseService {

    private final Map<String, UserRecord> users = new HashMap<>();

    public boolean existsByEmail(String email) {
        return users.containsKey(key(email));
    }

    public void save(UserRecord user) {
        users.put(key(user.email()), user);
    }

    public Optional<UserRecord> findByEmail(String email) {
        return Optional.ofNullable(users.get(key(email)));
    }

    public void markVerified(String email) {
        users.computeIfPresent(key(email), (k, user) -> user.asVerified());
    }

    public void deleteByEmail(String email) {
        users.remove(key(email));
    }

    private String key(String email) {
        return email.toLowerCase();
    }
}
