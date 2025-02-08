package business.customer;

import common.dto.result.Result;
import common.exception.ASException;

public interface CustomerAS {

    public Result<CustomerDTO> createCustomer(CustomerDTO customerDTO);

    public Result<CustomerDTO> readCustomer(int id);

    public Result<Void> deleteCustomer(int id);
}
