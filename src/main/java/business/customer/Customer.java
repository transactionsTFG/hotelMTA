package business.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.List;
import business.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NamedQueries({
        @NamedQuery(name = "business.customer.getByDni", query = "SELECT c FROM Customer c WHERE c.dni = :dni")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer implements Serializable {

    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Version
    private int version;

    private String name;
    
    private String email;
    
    private String phone;
    
    private String dni;
    
    private boolean available;
    
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

}
