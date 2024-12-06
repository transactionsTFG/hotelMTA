package tfg.hotelmta.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;
import tfg.hotelmta.business.BusinessFactory;
import tfg.hotelmta.business.booking.BookingAS;
import tfg.hotelmta.business.booking.BookingDTO;
import tfg.hotelmta.business.room.RoomAS;
import tfg.hotelmta.business.room.RoomDTO;

@WebService(serviceName = "HotelWS")
public class HotelWS {
    private final BookingAS bookingAS;
    private final RoomAS roomAS;
    
    public HotelWS() {
        this.bookingAS = BusinessFactory.getInstance().createBookingAS();
        this.roomAS = BusinessFactory.getInstance().createRoomAS();
    }
    
    @WebMethod(operationName = "searchRoom")
    public void searchRoom(int roomID) {
        this.roomAS.readRoom(roomID);
    }
    
    @WebMethod(operationName = "makeBooking")
    public void makeBooking(BookingDTO booking, List<RoomDTO> rooms) {
        this.bookingAS.createBooking(booking, rooms);
    }
    @WebMethod(operationName = "modifyBooking")
    public void modifyBooking(BookingDTO booking, List<RoomDTO> rooms) {
        this.bookingAS.updateBooking(booking, rooms);
    }
    @WebMethod(operationName = "cancelBooking")
    public void cancelBooking(int bookingID) {
        this.bookingAS.deleteBooking(bookingID);
    }
    @WebMethod(operationName = "readBooking")
    public void readBooking(int bookingID) {
        this.bookingAS.readBooking(bookingID);
    }
}
