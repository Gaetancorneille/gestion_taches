package gestionnaire_taches.util;

import java.util.regex.Pattern;

public class ValidationUtils {
    
    // Email pattern
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    // Password pattern (at least 8 characters, one uppercase, one lowercase, one digit)
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    
    // Phone pattern (basic international format)
    private static final String PHONE_PATTERN = 
        "^\\+?[1-9]\\d{1,14}$";
    
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    
    /**
     * Validate email format
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return emailPattern.matcher(email.trim()).matches();
    }
    
    /**
     * Validate password strength
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return passwordPattern.matcher(password).matches();
    }
    
    /**
     * Validate phone number format
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // Phone is optional
        }
        return phonePattern.matcher(phone.trim()).matches();
    }
    
    /**
     * Validate that a string is not null or empty
     * @param str the string to validate
     * @param fieldName the field name for error messages
     * @return true if valid, false otherwise
     */
    public static boolean isNotNullOrEmpty(String str, String fieldName) {
        if (str == null || str.trim().isEmpty()) {
            System.err.println(fieldName + " ne peut pas être nul ou vide");
            return false;
        }
        return true;
    }
    
    /**
     * Validate string length
     * @param str the string to validate
     * @param fieldName the field name for error messages
     * @param minLength minimum length
     * @param maxLength maximum length
     * @return true if valid, false otherwise
     */
    public static boolean isValidLength(String str, String fieldName, int minLength, int maxLength) {
        if (str == null) {
            System.err.println(fieldName + " ne peut pas être nul");
            return false;
        }
        
        if (str.length() < minLength) {
            System.err.println(fieldName + " doit comporter au moins " + minLength + " caractères");
            return false;
        }
        
        if (str.length() > maxLength) {
            System.err.println(fieldName + " ne doit pas dépasser " + maxLength + " caractères");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate that a number is positive
     * @param number the number to validate
     * @param fieldName the field name for error messages
     * @return true if valid, false otherwise
     */
    public static boolean isPositive(int number, String fieldName) {
        if (number <= 0) {
            System.err.println(fieldName + " doit être positif");
            return false;
        }
        return true;
    }
    
    /**
     * Validate that a number is non-negative
     * @param number the number to validate
     * @param fieldName the field name for error messages
     * @return true if valid, false otherwise
     */
    public static boolean isNonNegative(int number, String fieldName) {
        if (number < 0) {
            System.err.println(fieldName + " ne peut pas être négatif");
            return false;
        }
        return true;
    }
}