package business.validators;

public class Validator {

    // DD-MM-YYYY
    public static boolean isDate(String date) {
        if (date.isBlank() || date.isEmpty()) {
            return false;
        }

        return date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    public static boolean isEmail(String email) {
        if (email.isBlank() || email.isEmpty()) {
            return false;
        }
        return email.matches("");
    }

    public static boolean isPhone(String phone) {
        if (phone.isBlank() || phone.isEmpty()) {
            return false;
        }
        return phone.matches("\\d{9}");
    }

    public static boolean isDni(String dni) {
        if (dni.isBlank() || dni.isEmpty()) {
            return false;
        }
        return dni.matches("[A-Z]{8}\\d");
    }

    public static boolean hasSQLInjection(String str) {
        return false;
//return !str.matches("");
    }
}
