package soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;
import business.BusinessFactory;
import business.booking.BookingAS;
import business.booking.BookingDTO;
import business.booking.BookingTOA;
import business.room.RoomDTO;

@WebService(serviceName = "BookingWSB")
public class BookingWSB {

    private final BookingAS bookingAS;

    public BookingWSB() {
        this.bookingAS = BusinessFactory.getInstance().createBookingAS();
    }

    @WebMethod(operationName = "makeBooking")
    public int makeBooking(BookingDTO booking, List<RoomDTO> rooms) {
        return this.bookingAS.createBooking(booking, rooms);
    }

    @WebMethod(operationName = "modifyBooking")
    public int modifyBooking(BookingDTO booking, List<RoomDTO> rooms) {
        return this.bookingAS.updateBooking(booking, rooms);
    }

    @WebMethod(operationName = "cancelBooking")
    public int cancelBooking(int bookingID) {
        return this.bookingAS.deleteBooking(bookingID);
    }

    @WebMethod(operationName = "searchBooking")
    public BookingTOA searchBooking(int bookingID) {
        return this.bookingAS.readBooking(bookingID);
    }
}
