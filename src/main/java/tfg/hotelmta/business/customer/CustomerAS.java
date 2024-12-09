package tfg.hotelmta.business.customer;

public interface CustomerAS {
    public int createCustomer(CustomerDTO customerDTO);
    public CustomerDTO readCustomer(int id);
    public int deleteCustomer(int id);
}
