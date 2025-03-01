// package business.customer;

// import common.consts.ASError;
// import common.dto.result.Result;
// import common.exception.ASException;
// import common.exception.CustomerASException;
// import common.validators.Validator;
// import javax.ejb.Stateless;
// import javax.inject.Inject;
// import javax.persistence.EntityManager;
// import javax.persistence.LockModeType;
// import javax.persistence.TypedQuery;

// @Stateless
// public class CustomerASImp implements CustomerAS {

//     private EntityManager em;

//     public CustomerASImp(){}

//     @Inject
//     public CustomerASImp(EntityManager em) {
//         this.em = em;
//     }

//     @Override
//     public Result<CustomerDTO> createCustomer(CustomerDTO customerDTO) {

//         this.isValid(customerDTO);

//         TypedQuery<Customer> query = em.createNamedQuery("business.customer.getByDni", Customer.class);
//         query.setParameter("dni", customerDTO.getDni());
//         Customer customer = query.getResultList().isEmpty() ? null : query.getResultList().get(0);

//         if (customer == null) {
//             customer = new Customer(customerDTO);
//             em.persist(customer);
//             return Result.success(customer.toDTO());
//         }

//         if (customer.isAvailable()) {
//             throw new CustomerASException(ASError.ACTIVE_CUSTOMER);
//         }

//         customer.setName(customerDTO.getName());
//         customer.setEmail(customerDTO.getEmail());
//         customer.setPhone(customerDTO.getPhone());
//         customer.setDni(customerDTO.getDni());
//         customer.setAvailable(true);
//         em.persist(customer);
//         return Result.success(customer.toDTO());

//     }

//     @Override
//     public Result<CustomerDTO> readCustomer(int id) {

//         Customer customer = em.find(Customer.class, id, LockModeType.OPTIMISTIC);

//         if (customer == null) {
//             throw new CustomerASException(ASError.NON_EXISTENT_CUSTOMER);
//         }

//         return Result.success(customer.toDTO());

//     }

//     @Override
//     public Result<Void> deleteCustomer(int id) {

//         Customer customer = em.find(Customer.class, id);

//         if (customer == null) {
//             throw new CustomerASException(ASError.NON_EXISTENT_CUSTOMER);
//         }

//         if (!customer.isAvailable()) {
//             throw new CustomerASException(ASError.NON_ACTIVE_CUSTOMER);
//         }

//         customer.setAvailable(false);
//         return Result.success(null);

//     }

//     private void isValid(CustomerDTO customer) {
//         if (customer == null)
//             throw new ASException(ASError.NON_EXISTENT_CUSTOMER);
//         if (!Validator.isDni(customer.getDni()))
//             throw new ASException(ASError.INVALID_DNI);
//         if (!Validator.isEmail(customer.getEmail()))
//             throw new ASException(ASError.INVALID_EMAIL);
//         if (!Validator.isPhone(customer.getPhone()))
//             throw new ASException(ASError.INVALID_PHONE);
//     }

// }
