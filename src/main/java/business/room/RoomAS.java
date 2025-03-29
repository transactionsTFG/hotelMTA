package business.room;

import java.util.List;

import business.bookingline.BookingLineDTO;

public interface RoomAS {

    RoomDTO readRoom(long roomId);
    List<BookingLineDTO> readRoomsByBooking(long bookingID);
    List<RoomParamsDTO> readRooms(final String hotelName, final String countryName);
}
