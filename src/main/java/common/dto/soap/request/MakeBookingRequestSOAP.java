package common.dto.soap.request;

import java.util.List;

import business.booking.BookingDTO;
import business.customer.CustomerDTO;
import business.room.RoomDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MakeBookingRequestSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
public class MakeBookingRequestSOAP {
    @XmlElement
    private CustomerDTO customer;

    @XmlElement
    private BookingDTO booking;

    @XmlElement
    private List<RoomDTO> rooms;

    public MakeBookingRequestSOAP() {
    }

    public MakeBookingRequestSOAP(CustomerDTO customer, BookingDTO booking, List<RoomDTO> rooms) {
        this.customer = customer;
        this.booking = booking;
        this.rooms = rooms;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

}