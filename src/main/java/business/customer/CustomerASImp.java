package business.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import business.exception.ASException;
import business.utils.ErrorResponses;
import integration.transaction.Transaction;
import integration.transaction.TransactionManager;

public class CustomerASImp implements CustomerAS {

    @Override
    public int createCustomer(CustomerDTO customerDTO) {
        int res = ErrorResponses.SYNTAX_ERROR;

        if (!this.isValid(customerDTO)) {
            return res;
        }

        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();

        try {
            TypedQuery<Customer> query = em.createNamedQuery("business.customer.getByDni", Customer.class);
            query.setParameter("dni", customerDTO.getDni());
            Customer customer = query.getResultList().isEmpty() ? null : query.getResultList().getFirst();
            if (customer == null) {
                customer = new Customer(customerDTO);
                em.persist(customer);
                t.commit();
                res = customer.getId();
                customerDTO.setId(res);
            } else {
                if (customer.isActive()) {
                    res = ErrorResponses.ACTIVE_CUSTOMER;
                    throw new ASException("Customer already active");
                } else {
                    customer.setName(customerDTO.getName());
                    customer.setEmail(customerDTO.getEmail());
                    customer.setPhone(customerDTO.getPhone());
                    customer.setDni(customerDTO.getDni());
                    customer.setActive(true);
                    t.commit();
                    res = ErrorResponses.NON_ACTIVE_CUSTOMER;
                    customerDTO.setId(customer.getId());
                }
            }
        } catch (Exception e) {
            t.rollback();
        }

        return res;
    }

    @Override
    public CustomerDTO readCustomer(int id) {
        CustomerDTO customerDTO = null;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Customer customer = em.find(Customer.class, id, LockModeType.OPTIMISTIC);
            if (customer == null) {
                throw new ASException("Customer with id " + id + " does not exist");
            }
            t.commit();
            customerDTO = customer.toTransfer();
        } catch (Exception e) {
            t.rollback();
        }
        return customerDTO;
    }

    @Override
    public int deleteCustomer(int id) {
        int res = ErrorResponses.NON_EXISTENT_CUSTOMER;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                throw new ASException("Non existent customer");
            }

            if (!customer.isActive()) {
                res = ErrorResponses.NON_ACTIVE_CUSTOMER;
                throw new ASException("Non active customer");
            }

            customer.setActive(false);
            t.commit();
            res = id;

        } catch (Exception e) {
            if (!(e instanceof ASException)) {
                res = ErrorResponses.UNEXPECTED_ERROR;
            }
            t.rollback();
        }
        return res;
    }

    private boolean isValid(CustomerDTO customer) {
        return true;
    }

}
