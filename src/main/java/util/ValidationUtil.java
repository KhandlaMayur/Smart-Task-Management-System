package util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // =========================================
    // EMAIL PATTERN
    // =========================================

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    // =========================================
    // PASSWORD PATTERN
    // =========================================

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"
            );

    // =========================================
    // NAME PATTERN
    // =========================================

    private static final Pattern NAME_PATTERN =
            Pattern.compile(
                    "^[A-Za-z ]{2,50}$"
            );

    // =========================================
    // VALIDATE EMAIL
    // =========================================

    public static boolean isValidEmail(String email) {

        if (email == null
                || email.trim().isEmpty()) {

            return false;
        }

        return EMAIL_PATTERN
                .matcher(email)
                .matches();
    }

    // =========================================
    // VALIDATE PASSWORD
    // =========================================

    public static boolean isValidPassword(String password) {

        if (password == null
                || password.trim().isEmpty()) {

            return false;
        }

        return PASSWORD_PATTERN
                .matcher(password)
                .matches();
    }

    // =========================================
    // VALIDATE NAME
    // =========================================

    public static boolean isValidName(String name) {

        if (name == null
                || name.trim().isEmpty()) {

            return false;
        }

        return NAME_PATTERN
                .matcher(name)
                .matches();
    }

    // =========================================
    // CHECK EMPTY FIELD
    // =========================================

    public static boolean isEmpty(String value) {

        return value == null
                || value.trim().isEmpty();
    }

    // =========================================
    // VALIDATE TASK TITLE
    // =========================================

    public static boolean isValidTaskTitle(String title) {

        if (title == null
                || title.trim().isEmpty()) {

            return false;
        }

        return title.length() >= 3
                && title.length() <= 100;
    }

    // =========================================
    // VALIDATE TASK DESCRIPTION
    // =========================================

    public static boolean isValidTaskDescription(
            String description) {

        if (description == null
                || description.trim().isEmpty()) {

            return false;
        }

        return description.length() >= 5;
    }

    // =========================================
    // VALIDATE PRIORITY
    // =========================================

    public static boolean isValidPriority(
            String priority) {

        if (priority == null) {

            return false;
        }

        return priority.equalsIgnoreCase("Low")
                || priority.equalsIgnoreCase("Medium")
                || priority.equalsIgnoreCase("High");
    }

    // =========================================
    // VALIDATE STATUS
    // =========================================

    public static boolean isValidStatus(
            String status) {

        if (status == null) {

            return false;
        }

        return status.equalsIgnoreCase("Pending")
                || status.equalsIgnoreCase("In Progress")
                || status.equalsIgnoreCase("Completed");
    }

    // =========================================
    // VALIDATE FILE EXTENSION
    // =========================================

    public static boolean isValidFileExtension(
            String fileName) {

        if (fileName == null
                || fileName.trim().isEmpty()) {

            return false;
        }

        String lowerFileName =
                fileName.toLowerCase();

        return lowerFileName.endsWith(".jpg")
                || lowerFileName.endsWith(".jpeg")
                || lowerFileName.endsWith(".png")
                || lowerFileName.endsWith(".pdf")
                || lowerFileName.endsWith(".doc")
                || lowerFileName.endsWith(".docx")
                || lowerFileName.endsWith(".txt");
    }

    // =========================================
    // VALIDATE FILE SIZE
    // =========================================

    public static boolean isValidFileSize(
            long fileSize,
            long maxSizeInMB) {

        long maxSize =
                maxSizeInMB * 1024 * 1024;

        return fileSize <= maxSize;
    }

    // =========================================
    // VALIDATE CATEGORY NAME
    // =========================================

    public static boolean isValidCategoryName(
            String categoryName) {

        if (categoryName == null
                || categoryName.trim().isEmpty()) {

            return false;
        }

        return categoryName.length() >= 2
                && categoryName.length() <= 50;
    }

    // =========================================
    // VALIDATE NUMERIC VALUE
    // =========================================

    public static boolean isNumeric(String value) {

        if (value == null
                || value.trim().isEmpty()) {

            return false;
        }

        try {

            Integer.parseInt(value);

            return true;

        } catch (NumberFormatException e) {

            return false;
        }
    }
}