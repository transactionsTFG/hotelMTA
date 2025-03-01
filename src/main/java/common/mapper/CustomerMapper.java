package common.mapper;

import business.customer.Customer;
import business.customer.CustomerDTO;

public interface CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .dni(customer.getDni())
                .build();
    }
}
