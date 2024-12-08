package tfg.hotelmta.business.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;
import tfg.hotelmta.business.customer.Customer;
import tfg.hotelmta.business.room.Room;

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
    private String agencyName;
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
        this.setAgencyName(bookingDTO.getAgencyName());
        this.setActive(bookingDTO.isActive());
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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
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

    public BookingDTO toTransfer() {
        return new BookingDTO(id, date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId(), active);
    }

}
