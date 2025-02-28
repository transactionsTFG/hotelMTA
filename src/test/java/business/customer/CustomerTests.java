// package business.customer;

// import static org.junit.Assert.assertThrows;

// import org.junit.Assert;
// import org.junit.FixMethodOrder;
// import org.junit.Test;
// import org.junit.runners.MethodSorters;

// import common.dto.result.Result;
// import common.exception.ASException;
// import mocks.UnitTestASManager;

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
// public class CustomerTests extends UnitTestASManager {

//     private static final String name = "Juan", email = "juan@gmail.com", phone = "123456789", dni = "12345678";

//     private static int customerId = 0;
    
//     private CustomerDTO customer;

//     private void updateCustomerId() {
//         customerId++;
//     }

//     @Test
//     public void a1CreateCustomerOK() throws ASException {
//         customer = new CustomerDTO(name, email, phone, dni + "Z", true);
//         Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
//         updateCustomerId();
//     }

//     @Test
//     public void b2ReadCustomerOK() throws ASException {
//         customer = new CustomerDTO(name, email, phone, dni + "B", true);
//         Result<CustomerDTO> res = customerAS.createCustomer(customer);
//         Assert.assertTrue(res.isSuccess());
//         Assert.assertTrue(customerAS.readCustomer(customerId).isSuccess());
//     }

//     @Test
//     public void b3ReadCustomerKO() throws ASException {
//         assertThrows(ASException.class, () -> customerAS.readCustomer(-1));
//     }

// }
