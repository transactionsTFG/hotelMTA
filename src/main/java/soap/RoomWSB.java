package soap;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import business.room.RoomAS;
import business.room.RoomDTO;
import business.room.RoomParamsDTO;
import common.consts.WebMethodConsts;
import common.dto.result.Result;
import common.dto.soap.response.SoapResponse;
import common.mapper.SoapResponseMapper;

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

}
