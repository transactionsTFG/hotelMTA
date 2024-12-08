package tfg.hotelmta.business.validators;

public class Validator {

    // DD-MM-YYYY
    public static boolean isDate(String date) {
        if (date.isBlank() || date.isEmpty()) {
            return false;
        }

        return date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    public static boolean hasSQLInjection(String str) {
        return false;
//return !str.matches("");
    }
}
