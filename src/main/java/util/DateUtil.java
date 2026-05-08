package util;

import java.sql.Date;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;

public class DateUtil {

    // =========================================
    // DATE FORMATTERS
    // =========================================

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy");

    private static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    // =========================================
    // GET CURRENT DATE
    // =========================================

    public static LocalDate getCurrentDate() {

        return LocalDate.now();
    }

    // =========================================
    // GET CURRENT DATE TIME
    // =========================================

    public static LocalDateTime getCurrentDateTime() {

        return LocalDateTime.now();
    }

    // =========================================
    // FORMAT LOCAL DATE
    // =========================================

    public static String formatDate(LocalDate date) {

        if (date == null) {

            return "";
        }

        return date.format(DISPLAY_DATE_FORMATTER);
    }

    // =========================================
    // FORMAT LOCAL DATE TIME
    // =========================================

    public static String formatDateTime(LocalDateTime dateTime) {

        if (dateTime == null) {

            return "";
        }

        return dateTime.format(DISPLAY_DATE_TIME_FORMATTER);
    }

    // =========================================
    // PARSE STRING TO LOCAL DATE
    // =========================================

    public static LocalDate parseDate(String date) {

        try {

            return LocalDate.parse(
                    date,
                    DATE_FORMATTER
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================
    // PARSE STRING TO LOCAL DATE TIME
    // =========================================

    public static LocalDateTime parseDateTime(String dateTime) {

        try {

            return LocalDateTime.parse(
                    dateTime,
                    DATE_TIME_FORMATTER
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================
    // CONVERT LOCALDATE TO SQL DATE
    // =========================================

    public static Date convertToSqlDate(LocalDate localDate) {

        if (localDate == null) {

            return null;
        }

        return Date.valueOf(localDate);
    }

    // =========================================
    // CONVERT SQL DATE TO LOCALDATE
    // =========================================

    public static LocalDate convertToLocalDate(Date sqlDate) {

        if (sqlDate == null) {

            return null;
        }

        return sqlDate.toLocalDate();
    }

    // =========================================
    // CONVERT LOCALDATETIME TO TIMESTAMP
    // =========================================

    public static Timestamp convertToTimestamp(
            LocalDateTime localDateTime) {

        if (localDateTime == null) {

            return null;
        }

        return Timestamp.valueOf(localDateTime);
    }

    // =========================================
    // CONVERT TIMESTAMP TO LOCALDATETIME
    // =========================================

    public static LocalDateTime convertToLocalDateTime(
            Timestamp timestamp) {

        if (timestamp == null) {

            return null;
        }

        return timestamp.toLocalDateTime();
    }

    // =========================================
    // CHECK OVERDUE DATE
    // =========================================

    public static boolean isOverdue(LocalDate dueDate) {

        if (dueDate == null) {

            return false;
        }

        return dueDate.isBefore(LocalDate.now());
    }

    // =========================================
    // CHECK TODAY DATE
    // =========================================

    public static boolean isToday(LocalDate date) {

        if (date == null) {

            return false;
        }

        return date.isEqual(LocalDate.now());
    }

    // =========================================
    // CALCULATE DAYS BETWEEN
    // =========================================

    public static long getDaysBetween(LocalDate startDate,
                                      LocalDate endDate) {

        if (startDate == null || endDate == null) {

            return 0;
        }

        return ChronoUnit.DAYS.between(
                startDate,
                endDate
        );
    }

    // =========================================
    // GET REMAINING DAYS
    // =========================================

    public static long getRemainingDays(LocalDate dueDate) {

        if (dueDate == null) {

            return 0;
        }

        return ChronoUnit.DAYS.between(
                LocalDate.now(),
                dueDate
        );
    }

    // =========================================
    // FORMAT FOR INPUT FIELD
    // =========================================

    public static String formatForInput(LocalDate date) {

        if (date == null) {

            return "";
        }

        return date.format(DATE_FORMATTER);
    }

    // =========================================
    // FORMAT DATE TIME FOR INPUT
    // =========================================

    public static String formatDateTimeForInput(
            LocalDateTime dateTime) {

        if (dateTime == null) {

            return "";
        }

        return dateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        );
    }
}