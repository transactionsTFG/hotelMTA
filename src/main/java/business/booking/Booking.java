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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking implements Serializable {

    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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

    public BookingDTO toDTO() {
        return new BookingDTO(id, date, numberOfNights, withBreakfast, peopleNumber, customer.getId(),
                active);
    }

}
