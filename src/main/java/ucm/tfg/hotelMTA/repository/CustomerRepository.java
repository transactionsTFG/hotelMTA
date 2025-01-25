package ucm.tfg.hotelMTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfg.ucm.hotelmta.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    public Customer findByDni(String dni);
}
