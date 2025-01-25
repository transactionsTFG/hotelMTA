package ucm.tfg.hotelMTA.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import tfg.ucm.hotelmta.GetRoomRequest;
import tfg.ucm.hotelmta.GetRoomResponse;
import ucm.tfg.hotelMTA.repository.RoomRepository;

@Endpoint
public class RoomEndpoint {
    private static final String NAMESPACE_URI = "http://ucm.tfg/hotelMTA";

    private RoomRepository roomRepository;


    @Autowired
    public RoomEndpoint(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRoomRequest")
	@ResponsePayload
	public GetRoomResponse getCountry(@RequestPayload GetRoomRequest request) {
		GetRoomResponse response = new GetRoomResponse();
		response.setRoom(roomRepository.getRoom(request.getNumber()));

		return response;
	}
}
