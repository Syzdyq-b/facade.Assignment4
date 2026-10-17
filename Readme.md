Facade pattern: User Registration (Java)
Subsystem (4 classes): ValidationService, PasswordHasher, DatabaseService, EmailVerifier Facade: RegistrationFacade (registerUser, confirmEmail) Client: Main (facade only), AdvancedClientDemo (subsystem directly)

Compile: javac -d out $(find src -name '*.java') Run: java -cp out registration.client.Main