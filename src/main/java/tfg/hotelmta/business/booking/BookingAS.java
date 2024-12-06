package tfg.hotelmta.business.booking;

import java.util.List;
import tfg.hotelmta.business.room.RoomDTO;

public interface BookingAS {

    public int createBooking(BookingDTO bookingDTO, List<RoomDTO> rooms);

    public int updateBooking(BookingDTO bookingDTO, List<RoomDTO> rooms);

    public int deleteBooking(int id);

    public BookingTOA readBooking(int id);
}
