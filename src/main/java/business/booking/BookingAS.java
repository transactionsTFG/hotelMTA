package business.booking;

import common.dto.result.Result;
import common.dto.soap.request.MakeBookingRequestSOAP;
import common.dto.soap.request.ModifyBookingRequestSOAP;
import common.dto.soap.request.UserSOAP;

public interface BookingAS {

    public Result<BookingTOA> createBooking(MakeBookingRequestSOAP bookingSOAP, UserSOAP userSOAP);

    public Result<BookingDTO> updateBooking(ModifyBookingRequestSOAP bookingSOAP);

    public Result<Double> deleteBooking(long id);

    public Result<Double> deleteBookingLine(long bookingId, long roomId);

    public Result<BookingTOA> readBooking(long id);
}
