package soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import business.BusinessFactory;
import business.room.RoomAS;
import business.room.RoomDTO;

@WebService(serviceName = "RoomWSB")
public class RoomWSB {
    private final RoomAS roomAS;
    
    public RoomWSB() {
        this.roomAS = BusinessFactory.getInstance().createRoomAS();
    }
    
    @WebMethod(operationName = "searchRoom")
    public RoomDTO searchRoom(int roomId) {
        return this.roomAS.readRoom(roomId);
    }
    
}
