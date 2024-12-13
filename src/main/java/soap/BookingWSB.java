package soap;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

import business.booking.BookingAS;
import business.booking.BookingDTO;
import business.booking.BookingTOA;
import business.room.RoomDTO;
import common.consts.WebMethodConsts;

@WebService(serviceName = "BookingWSB")
public class BookingWSB {

    private final BookingAS bookingAS;

    @Inject
    public BookingWSB(final BookingAS bookingAS) {
        this.bookingAS = bookingAS;
    }

    @WebMethod(operationName = WebMethodConsts.MAKE_BOOKING)
    public int makeBooking(BookingDTO booking, List<RoomDTO> rooms) {
        return this.bookingAS.createBooking(booking, rooms);
    }

    @WebMethod(operationName = WebMethodConsts.MODIFY_BOOKING)
    public int modifyBooking(BookingDTO booking, List<RoomDTO> rooms) {
        return this.bookingAS.updateBooking(booking, rooms);
    }

    @WebMethod(operationName = WebMethodConsts.CANCEL_BOOKING)
    public int cancelBooking(int bookingID) {
        return this.bookingAS.deleteBooking(bookingID);
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_BOOKING)
    public BookingTOA searchBooking(int bookingID) {
        return this.bookingAS.readBooking(bookingID);
    }
}
