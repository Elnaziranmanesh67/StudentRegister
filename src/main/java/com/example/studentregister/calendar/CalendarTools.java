
package com.example.studentregister.calendar;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public final class CalendarTools {

    private CalendarTools() {
    }

    private static final Locale ENGLISH = Locale.ENGLISH;
    private static final ZoneId ZONE = ZoneId.systemDefault();

    private static final int[] JALALI_BREAKS = {
            -61, 9, 38, 199, 426, 686, 756, 818, 1111,
            1181, 1210, 1635, 2060, 2097, 2192, 2262,
            2324, 2394, 2456, 3178
    };

    public enum DateTimeUnitEnum {
        SECOND, MINUTE, HOUR, DAY, MONTH, YEAR
    }

    public static String fixOneDigit(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        return value.length() == 1 ? "0" + value : value;
    }

    public static String fixDateSlash(String date, String delimiter) {
        if (date == null || date.isBlank()) {
            return date;
        }

        String[] parts = date.split("/", -1);

        if (parts.length != 3) {
            return date;
        }

        return parts[0] + delimiter
                + fixOneDigit(parts[1]) + delimiter
                + fixOneDigit(parts[2]);
    }

    public static String getCurrentGregorianDateString(String delimiter) {
        return format(
                new Date(),
                "yyyy" + delimiter + "MM" + delimiter + "dd",
                ENGLISH,
                new GregorianCalendar(ENGLISH)
        );
    }

    public static String getCurrentGregorianDateTimeString(String delimiter) {
        return format(
                new Date(),
                "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss",
                ENGLISH,
                new GregorianCalendar(ENGLISH)
        );
    }

    public static String getCurrentJalaliDateString(String delimiter) {
        Locale locale = Locale.forLanguageTag("fa-IR");

        return format(
                new Date(),
                "yyyy" + delimiter + "MM" + delimiter + "dd",
                locale,
                Calendar.getInstance(locale)
        );
    }

    public static String getCurrentJalaliDateTimeString(String delimiter) {
        Locale locale = Locale.forLanguageTag("fa-IR");

        return format(
                new Date(),
                "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss",
                locale,
                Calendar.getInstance(locale)
        );
    }

    public static String fixToLocaleDate(
            Date date, String delimiter, Locale locale) {

        if (date == null) {
            return null;
        }

        Locale currentLocale = resolveLocale(locale);

        return format(
                date,
                "yyyy" + delimiter + "MM" + delimiter + "dd",
                currentLocale,
                Calendar.getInstance(currentLocale)
        );
    }

    public static String fixToLocaleDate(
            String sourceDate, String delimiter, Locale locale) {

        if (sourceDate == null || sourceDate.isBlank()) {
            return sourceDate;
        }

        Locale currentLocale = resolveLocale(locale);
        String normalized = sourceDate.replace('/', '-');
        String[] parts = normalized.split("-", -1);

        if (parts.length != 3) {
            return sourceDate;
        }

        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            LocalDate date;

            if (year > 1500) {
                date = LocalDate.of(year, month, day);
            } else {
                date = jalaliToLocalDate(year, month, day);
            }

            Date convertedDate = Date.from(
                    date.atStartOfDay(ZONE).toInstant()
            );

            return fixToLocaleDate(convertedDate, delimiter, currentLocale);

        } catch (RuntimeException ex) {
            return sourceDate;
        }
    }

    public static String fixToLocaleDateTime(
            Date date, String delimiter, Locale locale) {

        if (date == null) {
            return null;
        }

        Locale currentLocale = resolveLocale(locale);

        return format(
                date,
                "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss",
                currentLocale,
                Calendar.getInstance(currentLocale)
        );
    }

    public static String fixToLocaleDateTime(
            String source, String delimiter, Locale locale) {

        if (source == null || source.isBlank()) {
            return source;
        }

        String[] parts = source.trim().split("\\s+", 2);

        if (parts.length != 2) {
            return source;
        }

        String date = fixToLocaleDate(parts[0], delimiter, locale);

        return date.equals(parts[0])
                && date.equals(source.split("\\s+", 2)[0])
                ? date + " " + parts[1]
                : date + " " + parts[1];
    }

    public static Date jalaliToGregorianDate(
            String sourceDate, String delimiter) {

        if (sourceDate == null || sourceDate.isBlank()) {
            return null;
        }

        String[] parts = sourceDate.replace(delimiter, "/").split("/", -1);

        if (parts.length != 3) {
            return null;
        }

        try {
            LocalDate date = jalaliToLocalDate(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

            return Date.from(date.atStartOfDay(ZONE).toInstant());

        } catch (RuntimeException ex) {
            return null;
        }
    }

    public static String jalaliToGregorianDate(
            String sourceDate,
            String sourceDelimiter,
            String destinationDelimiter) {

        Date date = jalaliToGregorianDate(sourceDate, sourceDelimiter);

        if (date == null) {
            return null;
        }

        return format(
                date,
                "yyyy" + destinationDelimiter + "MM"
                        + destinationDelimiter + "dd",
                ENGLISH,
                new GregorianCalendar(ENGLISH)
        );
    }

    public static String gregorianToJalaliDate(
            String sourceDate,
            String sourceDelimiter,
            String destinationDelimiter) {

        Date date = parseDate(sourceDate, sourceDelimiter);

        if (date == null) {
            return null;
        }

        Locale locale = Locale.forLanguageTag("fa-IR");

        return format(
                date,
                "yyyy" + destinationDelimiter + "MM"
                        + destinationDelimiter + "dd",
                locale,
                Calendar.getInstance(locale)
        );
    }

    public static long getTwoDateDifference(
            Date date1, Date date2, DateTimeUnitEnum unit) {

        if (date1 == null || date2 == null || unit == null) {
            return 0;
        }

        long difference = date2.getTime() - date1.getTime();

        return switch (unit) {
            case SECOND -> difference / 1_000;
            case MINUTE -> difference / 60_000;
            case HOUR -> difference / 3_600_000;
            case DAY -> difference / 86_400_000;
            case MONTH -> difference / 2_592_000_000L;
            case YEAR -> difference / 31_104_000_000L;
        };
    }

    public static boolean checkJalaliDateValidity(
            Integer year, Integer month, Integer day) {

        if (year == null || month == null || day == null
                || year <= 0 || month < 1 || month > 12 || day < 1) {
            return false;
        }

        try {
            jalaliToLocalDate(year, month, day);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public static boolean checkGregorianDateValidity(
            Integer year, Integer month, Integer day) {

        if (year == null || month == null || day == null
                || year <= 0 || month < 1 || month > 12 || day < 1) {
            return false;
        }

        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private static Locale resolveLocale(Locale locale) {
        return locale != null
                ? locale
                : LocaleContextHolder.getLocale();
    }

    private static String format(
            Date date, String pattern, Locale locale, Calendar ignoredCalendar) {

        if (date == null) {
            return null;
        }

        if (!"fa".equals(locale.getLanguage())) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
            formatter.setTimeZone(TimeZone.getTimeZone(ZONE));
            return formatter.format(date);
        }

        LocalDate gregorianDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZONE)
                .toLocalDate();

        int[] jalali = gregorianToJalali(
                gregorianDate.getYear(),
                gregorianDate.getMonthValue(),
                gregorianDate.getDayOfMonth()
        );

        int firstDelimiterEnd = pattern.indexOf("MM");
        String delimiter1 = pattern.substring(4, firstDelimiterEnd);

        int secondDelimiterStart = firstDelimiterEnd + 2;
        int secondDelimiterEnd = pattern.indexOf("dd", secondDelimiterStart);
        String delimiter2 = pattern.substring(secondDelimiterStart, secondDelimiterEnd);

        String result = String.format(
                Locale.ROOT,
                "%04d%s%02d%s%02d",
                jalali[0], delimiter1, jalali[1], delimiter2, jalali[2]
        );

        if (pattern.contains("HH")) {
            SimpleDateFormat timeFormatter =
                    new SimpleDateFormat("HH:mm:ss", Locale.ROOT);
            timeFormatter.setTimeZone(TimeZone.getTimeZone(ZONE));
            result += " " + timeFormatter.format(date);
        }

        return result;
    }

    private static Date parseDate(String date, String delimiter) {
        if (date == null || date.isBlank()
                || delimiter == null || delimiter.isEmpty()) {
            return null;
        }

        String pattern = "yyyy" + delimiter + "MM" + delimiter + "dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, ENGLISH);
        formatter.setLenient(false);
        formatter.setTimeZone(TimeZone.getTimeZone(ZONE));

        ParsePosition position = new ParsePosition(0);
        Date result = formatter.parse(date, position);

        if (result == null || position.getIndex() != date.length()) {
            return null;
        }

        return result;
    }

    @NotNull
    public static Date getDateFromCustomDate(@NotNull CustomDate customDate) {
        Objects.requireNonNull(customDate, "customDate");

        LocalDate date = LocalDate.of(
                customDate.getYear(),
                customDate.getMonth(),
                customDate.getDay()
        );

        return Date.from(date.atStartOfDay(ZONE).toInstant());
    }

    // -------------------- Jalali conversion --------------------

    private static LocalDate jalaliToLocalDate(
            int jy, int jm, int jd) {

        if (jm < 1 || jm > 12 || jd < 1 || jd > 31) {
            throw new IllegalArgumentException("Invalid Jalali date");
        }

        int[] result = jalaliToGregorian(jy, jm, jd);
        LocalDate date = LocalDate.of(result[0], result[1], result[2]);

        int[] verified = gregorianToJalali(
                date.getYear(), date.getMonthValue(), date.getDayOfMonth()
        );

        if (verified[0] != jy || verified[1] != jm || verified[2] != jd) {
            throw new IllegalArgumentException("Invalid Jalali date");
        }

        return date;
    }

    private static int[] gregorianToJalali(int gy, int gm, int gd) {
        int[] cal = jalaliCalendar(gy);
        int jy = gy - 621;
        int march = cal[1];

        int gDayNo = gregorianDayNumber(gy, gm, gd);
        int marchDayNo = gregorianDayNumber(gy, 3, march);
        int k = gDayNo - marchDayNo;

        int jm;
        int jd;

        if (k >= 0) {
            if (k <= 185) {
                jm = 1 + k / 31;
                jd = k % 31 + 1;
                return new int[]{jy, jm, jd};
            }

            k -= 186;
        } else {
            jy--;
            k += 179;

            if (cal[0] == 1) {
                k++;
            }
        }

        jm = 7 + k / 30;
        jd = k % 30 + 1;

        return new int[]{jy, jm, jd};
    }

    private static int[] jalaliToGregorian(int jy, int jm, int jd) {
        int[] cal = jalaliCalendar(jy);
        int gy = cal[2];
        int march = cal[1];

        int dayNumber = gregorianDayNumber(gy, 3, march)
                + (jm - 1) * 31
                - (jm / 7) * (jm - 7)
                + jd - 1;

        return dayNumberToGregorian(dayNumber);
    }

    // Returns {leap, march, Gregorian year}
    private static int[] jalaliCalendar(int jy) {
        if (jy < JALALI_BREAKS[0]
                || jy >= JALALI_BREAKS[JALALI_BREAKS.length - 1]) {
            throw new IllegalArgumentException("Jalali year out of supported range");
        }

        int gy = jy + 621;
        int leapJ = -14;
        int jp = JALALI_BREAKS[0];
        int jump = 0;

        for (int i = 1; i < JALALI_BREAKS.length; i++) {
            int jm = JALALI_BREAKS[i];
            jump = jm - jp;

            if (jy < jm) {
                break;
            }

            leapJ += (jump / 33) * 8 + (jump % 33) / 4;
            jp = jm;
        }

        int n = jy - jp;
        leapJ += (n / 33) * 8 + ((n % 33) + 3) / 4;

        if (jump % 33 == 4 && jump - n == 4) {
            leapJ++;
        }

        int leapG = gy / 4 - ((gy / 100 + 1) * 3) / 4 - 150;
        int march = 20 + leapJ - leapG;

        if (jump - n < 6) {
            n = n - jump + ((jump + 4) / 33) * 33;
        }

        int leap = Math.floorMod(Math.floorMod(n + 1, 33) - 1, 4);

        if (leap == -1) {
            leap = 4;
        }

        return new int[]{leap, march, gy};
    }

    private static int gregorianDayNumber(int gy, int gm, int gd) {
        int a = (14 - gm) / 12;
        int y = gy + 4800 - a;
        int m = gm + 12 * a - 3;

        return gd + (153 * m + 2) / 5
                + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
    }

    private static int[] dayNumberToGregorian(int jdn) {
        int a = jdn + 32044;
        int b = (4 * a + 3) / 146097;
        int c = a - (146097 * b) / 4;
        int d = (4 * c + 3) / 1461;
        int e = c - (1461 * d) / 4;
        int m = (5 * e + 2) / 153;

        int day = e - (153 * m + 2) / 5 + 1;
        int month = m + 3 - 12 * (m / 10);
        int year = 100 * b + d - 4800 + m / 10;

        return new int[]{year, month, day};
    }
}
