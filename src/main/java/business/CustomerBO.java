package business;


import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "CustomerBO.findByid", query = "select obj from CustomerBO obj where :id = obj.id "),
		@NamedQuery(name = "CustomerBO.findByname", query = "select obj from CustomerBO obj where :name = obj.name "),
		@NamedQuery(name = "CustomerBO.findByemail", query = "select obj from CustomerBO obj where :email = obj.email "),
		@NamedQuery(name = "CustomerBO.findByphone", query = "select obj from CustomerBO obj where :phone = obj.phone "),
		@NamedQuery(name = "CustomerBO.findBydni", query = "select obj from CustomerBO obj where :dni = obj.dni "),
		@NamedQuery(name = "CustomerBO.findByflightInstanceBO", query = "select obj from CustomerBO obj where :flightInstanceBO = obj.flightInstanceBO "),
		@NamedQuery(name = "CustomerBO.findByreservationBO", query = "select obj from CustomerBO obj where :reservationBO MEMBER OF obj.reservationBO "),
		@NamedQuery(name = "CustomerBO.findBybookingBO", query = "select obj from CustomerBO obj where :bookingBO MEMBER OF obj.bookingBO ") })
public class CustomerBO implements Serializable {
	private static final long serialVersionUID = 0;

	public CustomerBO() {}

	@Id
	private int id;
	private String name;
	private String email;
	private String phone;
	private String dni;
	@OneToMany(mappedBy = "customerBO")
	private List<BookingBO> bookingBO;

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
	public List<BookingBO> getBookingBO() {
		return bookingBO;
	}
	public void setBookingBO(List<BookingBO> bookingBO) {
		this.bookingBO = bookingBO;
	}
	
	
	
}