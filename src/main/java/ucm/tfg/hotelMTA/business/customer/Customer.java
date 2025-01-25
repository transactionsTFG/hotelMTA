package ucm.tfg.hotelMTA.business.customer;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.Data;
import tfg.ucm.hotelmta.Booking;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Version
    private int version;
    private String name;
    private String email;
    private String phone;
    private String dni;
    private boolean active;
    @OneToMany(mappedBy = "customer")
    private List<Booking> booking;

    public CustomerDTO toDTO() {
        return new CustomerDTO(id, name, email, phone, dni, active);
    }

}
