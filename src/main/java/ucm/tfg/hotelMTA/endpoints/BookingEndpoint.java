package ucm.tfg.hotelMTA.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import tfg.ucm.hotelmta.GetBookingRequest;
import tfg.ucm.hotelmta.GetBookingResponse;
import ucm.tfg.hotelMTA.repository.BookingRepository;

@Endpoint
public class BookingEndpoint {
    private static final String NAMESPACE_URI = "http://ucm.tfg/hotelMTA";

    private BookingRepository bookingRepository;


    @Autowired
    public BookingEndpoint(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookingRequest")
	@ResponsePayload
	public GetBookingResponse getCountry(@RequestPayload GetBookingRequest request) {
		GetBookingResponse response = new GetBookingResponse();
		response.setBooking(bookingRepository.getBooking(request.getId()));

		return response;
	}
}
