package ucm.tfg.hotelMTA;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import tfg.ucm.hotelmta.Customer;

@Component
public class CustomerRepository {
    private static final Map<String, Customer> customers = new HashMap<>();

    @PostConstruct
    public void init() {
        Customer customer = new Customer();
        customer.setDni("12345678A");
        customer.setName("John Doe");
        customer.setEmail("9l4n2@example.com");
        customer.setPhone("123456789");
        customer.setActive(true);
        customers.put(customer.getDni(), customer);
    }

    public Customer getCustomer(String dni) {
        return customers.get(dni);
    }
}
