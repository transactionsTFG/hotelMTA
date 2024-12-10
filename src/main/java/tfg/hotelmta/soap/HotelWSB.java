package tfg.hotelmta.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;
import tfg.hotelmta.business.BusinessFactory;
import tfg.hotelmta.business.booking.BookingAS;
import tfg.hotelmta.business.booking.BookingDTO;
import tfg.hotelmta.business.booking.BookingTOA;
import tfg.hotelmta.business.room.RoomAS;
import tfg.hotelmta.business.room.RoomDTO;

@WebService(serviceName = "HotelWSB")
public class HotelWSB {

    private final BookingAS bookingAS;
    private final RoomAS roomAS;

    public HotelWSB() {
        this.bookingAS = BusinessFactory.getInstance().createBookingAS();
        this.roomAS = BusinessFactory.getInstance().createRoomAS();
    }

    @WebMethod(operationName = "searchRoom")
    public RoomDTO searchRoom(int roomID) {
        return this.roomAS.readRoom(roomID);
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
