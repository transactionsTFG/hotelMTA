package ucm.tfg.hotelMTA.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import tfg.ucm.hotelmta.GetCustomerRequest;
import tfg.ucm.hotelmta.GetCustomerResponse;
import ucm.tfg.hotelMTA.repository.CustomerRepository;

@Endpoint
public class CustomerEndpoint {
    private static final String NAMESPACE_URI = "http://ucm.tfg/hotelMTA";

    private CustomerRepository customerRepository;


    @Autowired
    public CustomerEndpoint(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
	@ResponsePayload
	public GetCustomerResponse getCountry(@RequestPayload GetCustomerRequest request) {
		GetCustomerResponse response = new GetCustomerResponse();
        response.setCustomer(customerRepository.getCustomer(request.getDni()));

		return response;
	}
}
