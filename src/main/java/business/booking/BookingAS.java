package business.booking;

import java.util.List;
import business.room.RoomDTO;
import common.dto.result.Result;
import common.exception.ASException;

public interface BookingAS {

    public Result<BookingDTO> createBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) throws ASException;

    public Result<BookingDTO> updateBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) throws ASException;

    public Result<Void> deleteBooking(int id) throws ASException;

    public Result<BookingTOA> readBooking(int id) throws ASException;
}
