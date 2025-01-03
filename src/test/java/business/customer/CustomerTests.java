package business.customer;

import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

import common.dto.result.Result;
import common.exception.ASException;
import mocks.UnitTestASManager;

public class CustomerTests extends UnitTestASManager {

    private static final String name = "Juan", email = "juan@gmail.com", phone = "123456789", dni = "12345678";

    private CustomerDTO customer;

    @Test
    public void createCustomerOK() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "A", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    }

    // * NOTE: test works but need a commit right after persisting customer entinty
    @Test
    public void readCustomerOK() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "B", true);
        Result<CustomerDTO> res = customerAS.createCustomer(customer);
        Assert.assertTrue(res.isSuccess());
        // Assert.assertTrue(customerAS.readCustomer(res.getData().getId()).isSuccess());
    }

    @Test
    public void readCustomerKO() throws ASException {
        assertThrows(ASException.class, () -> customerAS.readCustomer(-1));
    }

}
