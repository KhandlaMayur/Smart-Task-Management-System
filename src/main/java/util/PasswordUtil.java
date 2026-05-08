package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // =========================================
    // HASH PASSWORD
    // =========================================

    public static String hashPassword(String password) {

        if (password == null
                || password.trim().isEmpty()) {

            return null;
        }

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );
    }

    // =========================================
    // VERIFY PASSWORD
    // =========================================

    public static boolean verifyPassword(String plainPassword,
                                         String hashedPassword) {

        if (plainPassword == null
                || hashedPassword == null
                || plainPassword.trim().isEmpty()
                || hashedPassword.trim().isEmpty()) {

            return false;
        }

        try {

            return BCrypt.checkpw(
                    plainPassword,
                    hashedPassword
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // CHECK PASSWORD STRENGTH
    // =========================================

    public static boolean isStrongPassword(String password) {

        if (password == null) {

            return false;
        }

        // MINIMUM 8 CHARACTERS

        if (password.length() < 8) {

            return false;
        }

        // AT LEAST ONE UPPERCASE

        if (!password.matches(".*[A-Z].*")) {

            return false;
        }

        // AT LEAST ONE LOWERCASE

        if (!password.matches(".*[a-z].*")) {

            return false;
        }

        // AT LEAST ONE NUMBER

        if (!password.matches(".*\\d.*")) {

            return false;
        }

        // AT LEAST ONE SPECIAL CHARACTER

        if (!password.matches(".*[@#$%^&+=!].*")) {

            return false;
        }

        return true;
    }

    // =========================================
    // GENERATE TEMP PASSWORD
    // =========================================

    public static String generateTemporaryPassword() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        + "abcdefghijklmnopqrstuvwxyz"
                        + "0123456789"
                        + "@#$%!";

        StringBuilder tempPassword =
                new StringBuilder();

        for (int i = 0; i < 10; i++) {

            int index =
                    (int) (Math.random()
                            * characters.length());

            tempPassword.append(
                    characters.charAt(index)
            );
        }

        return tempPassword.toString();
    }
}