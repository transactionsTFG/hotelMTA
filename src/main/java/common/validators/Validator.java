package common.validators;

import java.time.LocalDate;

public class Validator {

    // DD-MM-YYYY
    public static boolean isDate(LocalDate date) {
        if (date == null || isBlankOrEmpty(date.toString())) {
            return false;
        }

        return new StringBuilder(date.toString()).reverse().toString().matches("\\d{2}-\\d{2}-\\d{4}");
    }

    public static boolean isEmail(String email) {
        if (isBlankOrEmpty(email)) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    public static boolean isPhone(String phone) {
        if (isBlankOrEmpty(phone)) {
            return false;
        }
        return phone.matches("\\d{9}");
    }

    public static boolean isDni(String dni) {
        if (isBlankOrEmpty(dni)) {
            return false;
        }
        return dni.matches("\\d{8}[A-Z]");
    }

    public static boolean isName(String name) {
        if (isBlankOrEmpty(name)) {
            return false;
        }
        return name.matches("[a-zA-Z ]+");
    }

    // TODO: need to be refactored
    public static boolean hasSQLInjection(String str) {
        if (isBlankOrEmpty(str)) {
            return false;
        }
        return str.matches("insert|select|delete|update");
    }

    private static boolean isBlankOrEmpty(String str) {
        return str.isBlank() || str.isEmpty();
    }
}
