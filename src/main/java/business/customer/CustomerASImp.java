package business.customer;

import common.consts.ASError;
import common.dto.result.Result;
import common.exception.ASException;
import common.exception.CustomerASException;
import common.validators.Validator;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Stateless
public class CustomerASImp implements CustomerAS {

    private final EntityManager em;

    @Inject
    public CustomerASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Result<CustomerDTO> createCustomer(CustomerDTO customerDTO) throws ASException {

        this.isValid(customerDTO);

        TypedQuery<Customer> query = em.createNamedQuery("business.customer.getByDni", Customer.class);
        query.setParameter("dni", customerDTO.getDni());
        Customer customer = query.getResultList().isEmpty() ? null : query.getResultList().getFirst();

        if (customer == null) {
            customer = new Customer(customerDTO);
            em.persist(customer);
            return Result.success(customer.toDTO());
        }

        if (customer.isActive()) {
            throw new CustomerASException(ASError.ACTIVE_CUSTOMER);
        }

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setDni(customerDTO.getDni());
        customer.setActive(true);
        em.persist(customer);
        return Result.success(customer.toDTO());

    }

    @Override
    @Transactional
    public Result<CustomerDTO> readCustomer(int id) throws ASException {

        Customer customer = em.find(Customer.class, id, LockModeType.OPTIMISTIC);

        if (customer == null) {
            throw new CustomerASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        return Result.success(customer.toDTO());

    }

    @Override
    @Transactional
    public Result<Void> deleteCustomer(int id) throws ASException {

        Customer customer = em.find(Customer.class, id);

        if (customer == null) {
            throw new CustomerASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isActive()) {
            throw new CustomerASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        customer.setActive(false);
        return Result.success(null);

    }

    private void isValid(CustomerDTO customer) throws ASException {
        if (customer == null)
            throw new ASException(ASError.NON_EXISTENT_CUSTOMER);
        if (!Validator.isDni(customer.getDni()))
            throw new ASException(ASError.INVALID_DNI);
        if (!Validator.isEmail(customer.getEmail()))
            throw new ASException(ASError.INVALID_EMAIL);
        if (!Validator.isPhone(customer.getPhone()))
            throw new ASException(ASError.INVALID_PHONE);
    }

}
