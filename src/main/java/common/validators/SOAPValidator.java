package common.validators;

import common.consts.ASError;
import common.dto.soap.request.MakeBookingRequestSOAP;
import common.dto.soap.request.ModifyBookingRequestSOAP;
import common.exception.BookingASException;

public class SOAPValidator {
    public static boolean makeBookingRequestIsValid(MakeBookingRequestSOAP booking) {
        if (booking == null) {
            throw new BookingASException(ASError.NON_EXISTENT_BOOKING);
        }

        if (!Validator.isDate(booking.getDate())) {
            throw new BookingASException(ASError.INVALID_DATE);
        }

        if (booking.getCustomerId() <= 0) {
            throw new BookingASException(ASError.INVALID_CUSTOMER_ID);
        }

        if (booking.getPeopleNumber() <= 0) {
            throw new BookingASException(ASError.INVALID_PEOPLE_NUMBER);
        }

        if (booking.getNumberOfNights() <= 0) {
            throw new BookingASException(ASError.INVALID_NUMBER_OF_NIGHTS);
        }
        return true;
    }
    public static boolean modifyBookingRequestIsValid(ModifyBookingRequestSOAP booking) {
        if (booking == null || booking.getId() <= 0) {
            throw new BookingASException(ASError.NON_EXISTENT_BOOKING);
        }

        if (!Validator.isDate(booking.getDate())) {
            throw new BookingASException(ASError.INVALID_DATE);
        }

        if (booking.getCustomerId() <= 0) {
            throw new BookingASException(ASError.INVALID_CUSTOMER_ID);
        }

        if (booking.getPeopleNumber() <= 0) {
            throw new BookingASException(ASError.INVALID_PEOPLE_NUMBER);
        }

        if (booking.getNumberOfNights() <= 0) {
            throw new BookingASException(ASError.INVALID_NUMBER_OF_NIGHTS);
        }
        return true;
    }
}
