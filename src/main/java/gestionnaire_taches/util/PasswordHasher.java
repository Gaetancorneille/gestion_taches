package gestionnaire_taches.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    
    /**
     * Hash a password using BCrypt
     * @param password the plain text password
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    /**
     * Verify a password against a hash
     * @param password the plain text password
     * @param hashedPassword the hashed password
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    /**
     * Check if a password needs to be rehashed (e.g., if BCrypt parameters have changed)
     * @param hashedPassword the hashed password
     * @return true if password should be rehashed
     */
    public static boolean needsRehash(String hashedPassword) {
        if (hashedPassword == null) {
            return false;
        }
        // BCrypt automatically handles this - we just return false for simplicity
        return false;
    }
}