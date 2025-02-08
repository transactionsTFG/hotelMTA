package business.booking;

import java.util.List;
import business.room.RoomDTO;
import common.dto.result.Result;
import common.exception.ASException;

public interface BookingAS {

    public Result<BookingTOA> createBooking(BookingDTO bookingDTO, List<RoomDTO> rooms);

    public Result<BookingTOA> updateBooking(BookingDTO bookingDTO, List<RoomDTO> rooms);

    public Result<Void> deleteBooking(int id);

    public Result<BookingTOA> readBooking(int id);
}
