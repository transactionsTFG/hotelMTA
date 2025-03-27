package business.room;

import java.util.List;

public interface RoomAS {

    RoomDTO readRoom(long roomId);
    List<RoomDTO> readRoomsByBooking(long bookingID);
    List<RoomParamsDTO> readRooms(final String hotelName, final String countryName);
}
