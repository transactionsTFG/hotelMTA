package soap;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import business.room.RoomAS;
import business.room.RoomDTO;
import common.consts.WebMethodConsts;
import common.dto.result.Result;
import common.dto.soap.response.RoomSOAP;
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
    public SoapResponse<RoomSOAP> searchRoom(int roomId) {
        try {
            final Result<RoomDTO> room = this.roomAS.readRoom(roomId);
            return SoapResponseMapper.toSoapResponse(room.getMessage(), RoomSOAP.toSOAP(room.getData()),
                    room.isSuccess());
        } catch (Exception e) {
            System.out.println("RoomWSB.searchRoom: " + e.getMessage());
            return SoapResponseMapper.toSoapResponse(e.getMessage(), null, false);
        }
    }

}
