package tfg.hotelmta.business.booking;

import java.util.List;
import tfg.hotelmta.business.customer.CustomerDTO;
import tfg.hotelmta.business.room.RoomDTO;

public class BookingTOA {
    private final BookingDTO booking;
    private final CustomerDTO customer;
    private final List<RoomDTO> rooms;
    
    public BookingTOA(BookingDTO booking, CustomerDTO customer, List<RoomDTO> rooms) {
        this.booking = booking;
        this.customer = customer;
        this.rooms = rooms;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }
    
}
