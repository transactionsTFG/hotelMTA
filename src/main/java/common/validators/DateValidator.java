package common.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.consts.ASError;
import common.exception.BookingASException;

public class DateValidator {
    public static void validateDates(String bsStartDateString, String bsEndDateString, String blStartDateString,
            String blEndDateString) throws BookingASException {
        final String pattern = "yyyy-MM-dd";
        try {
            Date bsStartDate = new SimpleDateFormat(pattern).parse(bsStartDateString);
            Date bsEndDate = new SimpleDateFormat(pattern).parse(bsEndDateString);
            Date blStartDate = new SimpleDateFormat(pattern).parse(blStartDateString);
            Date blEndDate = new SimpleDateFormat(pattern).parse(blEndDateString);
            if ((bsStartDate.before(blStartDate) && bsEndDate.before(blStartDate)) ||
                    (bsStartDate.after(blEndDate) && bsEndDate.after(blEndDate))) {

            } else {
                throw new BookingASException(ASError.ROOM_ALREADY_BOOKED);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
