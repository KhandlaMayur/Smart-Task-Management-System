package util;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public class EmailUtil {

    private static final Properties EMAIL_PROPERTIES =
            loadProperties();

    private EmailUtil() {
    }

    public static boolean isConfigured() {

        String enabled =
                EMAIL_PROPERTIES.getProperty(
                        "email.enabled",
                        "false"
                );

        String host =
                EMAIL_PROPERTIES.getProperty(
                        "email.smtp.host",
                        ""
                ).trim();

        String username =
                EMAIL_PROPERTIES.getProperty(
                        "email.smtp.username",
                        ""
                ).trim();

        String password =
                EMAIL_PROPERTIES.getProperty(
                        "email.smtp.password",
                        ""
                ).trim();

        return "true".equalsIgnoreCase(enabled)
                && !host.isEmpty()
                && !username.isEmpty()
                && !password.isEmpty();
    }

    public static boolean sendPasswordResetEmail(String toEmail,
                                                 String userName,
                                                 String resetUrl) {

        if (!isConfigured()) {

            return false;
        }

        String subject =
                "Smart Task Manager Password Reset";

        String body =
                "Hello " + (userName == null || userName.trim().isEmpty()
                        ? "User"
                        : userName.trim()) + ",\n\n"
                        + "We received a request to reset your Smart Task Manager password.\n"
                        + "Open the link below to set a new password:\n\n"
                        + resetUrl + "\n\n"
                        + "This link will expire in 15 minutes.\n"
                        + "If you did not request this reset, you can ignore this email.\n\n"
                        + "Smart Task Manager";

        try {

            sendEmail(toEmail, subject, body);

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    private static void sendEmail(String toEmail,
                                  String subject,
                                  String body)
            throws IOException {

        String host =
                EMAIL_PROPERTIES.getProperty("email.smtp.host").trim();

        int port =
                Integer.parseInt(
                        EMAIL_PROPERTIES.getProperty(
                                "email.smtp.port",
                                "465"
                        ).trim()
                );

        String username =
                EMAIL_PROPERTIES.getProperty("email.smtp.username").trim();

        String password =
                EMAIL_PROPERTIES.getProperty("email.smtp.password").trim();

        String from =
                EMAIL_PROPERTIES.getProperty(
                        "email.from",
                        username
                ).trim();

        SSLSocketFactory socketFactory =
                (SSLSocketFactory) SSLSocketFactory.getDefault();

        try (SSLSocket socket =
                     (SSLSocket) socketFactory.createSocket(host, port);
             BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     socket.getInputStream(),
                                     StandardCharsets.US_ASCII
                             )
                     );
             BufferedWriter writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     socket.getOutputStream(),
                                     StandardCharsets.US_ASCII
                             )
                     )) {

            socket.startHandshake();

            expectResponse(reader, "220");

            sendCommand(writer, reader, "EHLO localhost", "250");

            sendCommand(writer, reader, "AUTH LOGIN", "334");

            sendCommand(
                    writer,
                    reader,
                    encodeBase64(username),
                    "334"
            );

            sendCommand(
                    writer,
                    reader,
                    encodeBase64(password),
                    "235"
            );

            sendCommand(
                    writer,
                    reader,
                    "MAIL FROM:<" + from + ">",
                    "250"
            );

            sendCommand(
                    writer,
                    reader,
                    "RCPT TO:<" + toEmail + ">",
                    "250",
                    "251"
            );

            sendCommand(writer, reader, "DATA", "354");

            writer.write("From: " + from + "\r\n");
            writer.write("To: " + toEmail + "\r\n");
            writer.write("Subject: " + subject + "\r\n");
            writer.write("MIME-Version: 1.0\r\n");
            writer.write("Content-Type: text/plain; charset=UTF-8\r\n");
            writer.write("\r\n");
            writer.write(escapeBody(body));
            writer.write("\r\n.\r\n");
            writer.flush();

            expectResponse(reader, "250");

            sendCommand(writer, reader, "QUIT", "221");
        }
    }

    private static String escapeBody(String body) {

        String normalizedBody =
                body.replace("\r\n", "\n");

        String[] lines =
                normalizedBody.split("\n", -1);

        StringBuilder escapedBody =
                new StringBuilder();

        for (int i = 0; i < lines.length; i++) {

            String line =
                    lines[i];

            if (line.startsWith(".")) {

                escapedBody.append('.');
            }

            escapedBody.append(line);

            if (i < lines.length - 1) {

                escapedBody.append("\r\n");
            }
        }

        return escapedBody.toString();
    }

    private static void sendCommand(BufferedWriter writer,
                                    BufferedReader reader,
                                    String command,
                                    String... expectedCodes)
            throws IOException {

        writer.write(command);
        writer.write("\r\n");
        writer.flush();

        expectResponse(reader, expectedCodes);
    }

    private static void expectResponse(BufferedReader reader,
                                       String... expectedCodes)
            throws IOException {

        String response =
                readResponse(reader);

        for (String expectedCode : expectedCodes) {

            if (response.startsWith(expectedCode)) {

                return;
            }
        }

        throw new IOException(
                "SMTP command failed: " + response
        );
    }

    private static String readResponse(BufferedReader reader)
            throws IOException {

        String line =
                reader.readLine();

        if (line == null) {

            throw new IOException("No SMTP response received");
        }

        StringBuilder response =
                new StringBuilder(line);

        while (line.length() > 3
                && line.charAt(3) == '-') {

            line =
                    reader.readLine();

            if (line == null) {

                break;
            }

            response.append('\n')
                    .append(line);
        }

        return response.toString();
    }

    private static String encodeBase64(String value) {

        return Base64.getEncoder()
                .encodeToString(
                        value.getBytes(StandardCharsets.UTF_8)
                );
    }

    private static Properties loadProperties() {

        Properties properties =
                new Properties();

        try (InputStream inputStream =
                     EmailUtil.class.getClassLoader()
                             .getResourceAsStream("email.properties")) {

            if (inputStream != null) {

                properties.load(inputStream);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return properties;
    }
}
