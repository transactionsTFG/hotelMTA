package ucm.tfg.hotelMTA.business.booking;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Data;
import ucm.tfg.hotelMTA.business.customer.Customer;
import ucm.tfg.hotelMTA.business.room.Room;

@Data
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
    private List<Room> rooms;
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
    public BookingTOA toTOA() {
        return new BookingTOA(new BookingDTO(id, date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId(), active), customer.toDTO(), rooms.stream().map(Room::toDTO).toList());
    }
}
