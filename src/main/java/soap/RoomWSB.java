package soap;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import business.bookingline.BookingLineDTO;
import business.room.RoomAS;
import business.room.RoomDTO;
import business.room.RoomParamsDTO;
import common.consts.WebMethodConsts;

@WebService(serviceName = "RoomWSB")
public class RoomWSB {
    private final RoomAS roomAS;

    @Inject
    public RoomWSB(final RoomAS roomAS) {
        this.roomAS = roomAS;
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_ROOM)
    public RoomDTO searchRoom(long roomId) {
        final RoomDTO room = this.roomAS.readRoom(roomId);
        return room;
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_ROOMS)
    public List<RoomParamsDTO> searchRooms(final String hotelName, final String countryName) {
        return this.roomAS.readRooms(hotelName, countryName);
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_ROOMS_BY_BOOKING)
    public List<BookingLineDTO> searchRoomsByBooking(@WebParam(name = "bookingID") long bookingID) {
        return this.roomAS.readRoomsByBooking(bookingID);
    }

}
