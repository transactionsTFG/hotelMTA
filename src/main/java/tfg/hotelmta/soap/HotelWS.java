package tfg.hotelmta.soap;

import jakarta.jws.WebService;
import tfg.hotelmta.business.booking.BookingAS;
import tfg.hotelmta.business.room.RoomAS;

@WebService(serviceName = "HotelWS")
public class HotelWS {
    private BookingAS bookingAS;
    private RoomAS roomAS;
    
    public HotelWS() {
        
    }
}
