package soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import business.room.RoomAS;
import business.room.RoomDTO;
import common.consts.WebMethodConsts;

@WebService(serviceName = "RoomWSB")
public class RoomWSB {
    private final RoomAS roomAS;

    public RoomWSB(final RoomAS roomAS) {
        this.roomAS = roomAS;
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_ROOM)
    public RoomDTO searchRoom(int roomId) {
        return this.roomAS.readRoom(roomId);
    }

}
