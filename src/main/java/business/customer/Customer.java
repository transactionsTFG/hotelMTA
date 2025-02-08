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

@Entity
@NamedQueries({
        @NamedQuery(name = "business.customer.getByDni", query = "SELECT c FROM Customer c WHERE c.dni = :dni")
})
public class Customer implements Serializable {

    private static final long serialVersionUID = 0;

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

    public Customer() {
    }

    public Customer(CustomerDTO customerDTO) {
        this.setId(customerDTO.getId());
        this.setName(customerDTO.getName());
        this.setEmail(customerDTO.getEmail());
        this.setPhone(customerDTO.getPhone());
        this.setDni(customerDTO.getDni());
        this.setActive(customerDTO.isActive());
    }

    public Customer(String name, String email, String phone, String dni, boolean active) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dni = dni;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public CustomerDTO toDTO() {
        return new CustomerDTO(id, name, email, phone, dni, active);
    }

}
