package business.customer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import business.BusinessFactory;

public class CustomerTests {

    private static final String name = "Juan", email = "juan@gmail.com", phone = "123456789", dni = "12345678A";

    private CustomerAS customerAS;
    private CustomerDTO customer;

    @Before
    public void setUp() {
        customerAS = BusinessFactory.getInstance().createCustomerAS();
    }

    @Test
    public void createCustomerOK() {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer) > 0);
    }

    @Test
    public void readCustomerOK() {
        customer = new CustomerDTO(name, email, phone, dni, true);
        int res = customerAS.createCustomer(customer);
        Assert.assertTrue(res > 0);
        Assert.assertNotNull(customerAS.readCustomer(res));
    }

    @Test
    public void readCustomerKO() {
        Assert.assertNull(customerAS.readCustomer(-1));
    }

}
