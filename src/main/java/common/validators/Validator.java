package common.validators;

public class Validator {

    // DD-MM-YYYY
    public static boolean isDate(String date) {
        if (isBlankOrEmpty(date)) {
            return false;
        }

        return date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    public static boolean isEmail(String email) {
        if (isBlankOrEmpty(email)) {
            return false;
        }
        return email.matches("");
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
        return dni.matches("[A-Z]{8}\\d");
    }

    public static boolean isName(String name) {
        if (isBlankOrEmpty(name)) {
            return false;
        }
        return name.matches("[a-zA-Z ]+");
    }

    public static boolean hasSQLInjection(String str) {
        if (isBlankOrEmpty(str)) {
            return false;
        }
        return str.matches("[a-zA-Z0-9 ]+");
    }

    private static boolean isBlankOrEmpty(String str) {
        return str.isBlank() || str.isEmpty();
    }
}
