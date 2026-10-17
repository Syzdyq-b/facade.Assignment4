package registration.subsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmailVerifier {

    private final Map<String, String> tokens = new HashMap<>();

    public String sendVerificationEmail(String email) throws EmailDeliveryException {
        if (email.toLowerCase().endsWith("@unreachable.test")) {
            throw new EmailDeliveryException("Mail server rejected " + email);
        }
        String token = UUID.randomUUID().toString().substring(0, 8);
        tokens.put(email.toLowerCase(), token);
        System.out.println("   [EmailVerifier] email sent to " + email);
        return token;
    }

    public boolean verify(String email, String token) {
        String expected = tokens.get(email.toLowerCase());
        return expected != null && expected.equals(token);
    }
}
