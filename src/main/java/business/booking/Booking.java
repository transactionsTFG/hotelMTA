package business.booking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.List;
import business.customer.Customer;
import business.room.Room;

@Entity
public class Booking implements Serializable {

    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Version
    private int version;
    private String date;
    private int numberOfNights;
    private boolean withBreakfast;
    private boolean active;
    @ManyToOne
    private Customer customer;
    @ManyToMany
    private List<Room> room;
    private int peopleNumber;

    public Booking() {
    }

    public Booking(BookingDTO bookingDTO) {
        this.setId(bookingDTO.getId());
        this.setDate(bookingDTO.getDate());
        this.setPeopleNumber(bookingDTO.getPeopleNumber());
        this.setNumberOfNights(bookingDTO.getNumberOfNights());
        this.setWithBreakfast(bookingDTO.isWithBreakfast());
        this.setActive(bookingDTO.isActive());
    }

    public Booking(String date, int numberOfNights, boolean withBreakfast, int peopleNumber,
            boolean active) {
        this.date = date;
        this.numberOfNights = numberOfNights;
        this.withBreakfast = withBreakfast;
        this.peopleNumber = peopleNumber;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public boolean isWithBreakfast() {
        return withBreakfast;
    }

    public void setWithBreakfast(boolean withBreakfast) {
        this.withBreakfast = withBreakfast;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Room> getRoom() {
        return room;
    }

    public void setRoom(List<Room> room) {
        this.room = room;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public BookingDTO toDTO() {
        return new BookingDTO(id, date, numberOfNights, withBreakfast, peopleNumber, customer.getId(),
                active);
    }

}
