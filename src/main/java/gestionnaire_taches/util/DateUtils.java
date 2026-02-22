package gestionnaire_taches.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    /**
     * Format LocalDate to string
     * @param date the date to format
     * @return formatted date string
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Format LocalDateTime to string
     * @param dateTime the datetime to format
     * @return formatted datetime string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }
    
    /**
     * Parse string to LocalDate
     * @param dateString the date string to parse
     * @return parsed LocalDate
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString.trim(), DATE_FORMATTER);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + dateString + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get days between two dates
     * @param startDate start date
     * @param endDate end date
     * @return number of days between dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * Check if a date is in the past
     * @param date the date to check
     * @return true if date is in the past
     */
    public static boolean isPastDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if a date is in the future
     * @param date the date to check
     * @return true if date is in the future
     */
    public static boolean isFutureDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDate.now());
    }
    
    /**
     * Check if a date is today
     * @param date the date to check
     * @return true if date is today
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isEqual(LocalDate.now());
    }
    
    /**
     * Add days to a date
     * @param date the base date
     * @param days number of days to add
     * @return new date with days added
     */
    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }
    
    /**
     * Get the number of days until a future date
     * @param futureDate the future date
     * @return number of days until the date, negative if past
     */
    public static long daysUntil(LocalDate futureDate) {
        if (futureDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), futureDate);
    }
    
    /**
     * Check if a task is overdue
     * @param dueDate the due date
     * @return true if overdue
     */
    public static boolean isOverdue(LocalDate dueDate) {
        return isPastDate(dueDate) && !isToday(dueDate);
    }
}