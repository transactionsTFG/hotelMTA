package business.customer;

import common.dto.result.Result;
import common.exception.ASException;

public interface CustomerAS {

    public Result<CustomerDTO> createCustomer(CustomerDTO customerDTO) throws ASException;

    public Result<CustomerDTO> readCustomer(int id) throws ASException;

    public Result<Void> deleteCustomer(int id) throws ASException;
}
