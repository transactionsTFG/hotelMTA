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
import common.dto.soap.response.RoomSOAP;
import common.dto.soap.response.SoapResponse;
import common.mapper.RoomMapper;
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
        final Result<RoomDTO> room = this.roomAS.readRoom(roomId);
        return SoapResponseMapper.toSoapResponse(room.getMessage(), RoomMapper.fromDTOToRoomSOAP(room.getData()), room.isSuccess());
        // return SoapResponseMapper.toSoapResponse(room.getMessage(),
        //         RoomMapper.fromDTOToRoomSOAP(room.getData()),
        //         room.isSuccess());
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_ROOMS)
    public List<RoomParamsDTO> searchRooms(final String hotelName, final String countryName) {
        return this.roomAS.readRooms(hotelName, countryName);
    }

}
