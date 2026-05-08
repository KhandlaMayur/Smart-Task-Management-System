package util;

import model.User;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PasswordResetTokenStore {

    private static final long EXPIRY_MINUTES = 15;

    private static final Map<String, ResetTokenData> TOKEN_STORE =
            new ConcurrentHashMap<>();

    private PasswordResetTokenStore() {
    }

    public static String createToken(User user) {

        cleanupExpiredTokens();

        removeTokensForUser(user.getId());

        String token =
                UUID.randomUUID().toString();

        TOKEN_STORE.put(
                token,
                new ResetTokenData(
                        user.getId(),
                        user.getEmail(),
                        LocalDateTime.now()
                                .plusMinutes(EXPIRY_MINUTES)
                )
        );

        return token;
    }

    public static boolean isValid(String token) {

        ResetTokenData tokenData =
                TOKEN_STORE.get(token);

        if (tokenData == null) {

            return false;
        }

        if (tokenData.getExpiresAt().isBefore(LocalDateTime.now())) {

            TOKEN_STORE.remove(token);

            return false;
        }

        return true;
    }

    public static Integer getUserId(String token) {

        if (!isValid(token)) {

            return null;
        }

        return TOKEN_STORE.get(token).getUserId();
    }

    public static void consumeToken(String token) {

        TOKEN_STORE.remove(token);
    }

    private static void cleanupExpiredTokens() {

        Iterator<Map.Entry<String, ResetTokenData>> iterator =
                TOKEN_STORE.entrySet().iterator();

        LocalDateTime now =
                LocalDateTime.now();

        while (iterator.hasNext()) {

            Map.Entry<String, ResetTokenData> entry =
                    iterator.next();

            if (entry.getValue().getExpiresAt().isBefore(now)) {

                iterator.remove();
            }
        }
    }

    private static void removeTokensForUser(int userId) {

        Iterator<Map.Entry<String, ResetTokenData>> iterator =
                TOKEN_STORE.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, ResetTokenData> entry =
                    iterator.next();

            if (entry.getValue().getUserId() == userId) {

                iterator.remove();
            }
        }
    }

    private static class ResetTokenData {

        private final int userId;

        private final String email;

        private final LocalDateTime expiresAt;

        private ResetTokenData(int userId,
                               String email,
                               LocalDateTime expiresAt) {

            this.userId = userId;
            this.email = email;
            this.expiresAt = expiresAt;
        }

        public int getUserId() {

            return userId;
        }

        public String getEmail() {

            return email;
        }

        public LocalDateTime getExpiresAt() {

            return expiresAt;
        }
    }
}
