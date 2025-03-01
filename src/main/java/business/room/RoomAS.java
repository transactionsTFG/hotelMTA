package business.room;

import java.util.List;

public interface RoomAS {

    public RoomDTO readRoom(long roomId);

    List<RoomParamsDTO> readRooms(final String hotelName, final String countryName);
}
