package business.booking;

import common.dto.result.Result;
import common.dto.soap.request.MakeBookingRequestSOAP;
import common.dto.soap.request.ModifyBookingRequestSOAP;

public interface BookingAS {

    public Result<BookingTOA> createBooking(MakeBookingRequestSOAP bookingSOAP);

    public Result<BookingTOA> updateBooking(ModifyBookingRequestSOAP bookingSOAP);

    public Result<Void> deleteBooking(int id);

    public Result<BookingTOA> readBooking(int id);
}
