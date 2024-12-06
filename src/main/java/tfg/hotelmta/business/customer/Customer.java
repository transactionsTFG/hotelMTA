package tfg.hotelmta.business.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;
import tfg.hotelmta.business.booking.Booking;

@Entity
@NamedQueries({
    @NamedQuery(name = "CustomerBO.findByid", query = "select obj from CustomerBO obj where :id = obj.id "),
    @NamedQuery(name = "CustomerBO.findByname", query = "select obj from CustomerBO obj where :name = obj.name "),
    @NamedQuery(name = "CustomerBO.findByemail", query = "select obj from CustomerBO obj where :email = obj.email "),
    @NamedQuery(name = "CustomerBO.findByphone", query = "select obj from CustomerBO obj where :phone = obj.phone "),
    @NamedQuery(name = "CustomerBO.findBydni", query = "select obj from CustomerBO obj where :dni = obj.dni "),
    @NamedQuery(name = "CustomerBO.findByflightInstanceBO", query = "select obj from CustomerBO obj where :flightInstanceBO = obj.flightInstanceBO "),
    @NamedQuery(name = "CustomerBO.findByreservationBO", query = "select obj from CustomerBO obj where :reservationBO MEMBER OF obj.reservationBO "),
    @NamedQuery(name = "CustomerBO.findBybookingBO", query = "select obj from CustomerBO obj where :bookingBO MEMBER OF obj.bookingBO ")})
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

    public CustomerDTO toTransfer() {
        return new CustomerDTO(id, name, email, phone, dni, active);
    }

}