package common.dto.soap.response;

import business.booking.BookingDTO;
import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BookingSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class BookingSOAP {
    @XmlElement
    private long id;
    @XmlElement
    private String date;
    @XmlElement
    private int numberOfNights;
    @XmlElement
    private boolean withBreakfast;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private int customerId;
    @XmlElement
    private boolean active;

    public static BookingSOAP toSOAP(BookingDTO bookingDTO) {
        BookingSOAP bookingSOAP = new BookingSOAP();
        bookingSOAP.setId(bookingDTO.getId());
        bookingSOAP.setCustomerId(bookingDTO.getCustomerId());
        bookingSOAP.setDate(bookingDTO.getDate());
        bookingSOAP.setNumberOfNights(bookingDTO.getNumberOfNights());
        bookingSOAP.setPeopleNumber(bookingDTO.getPeopleNumber());
        bookingSOAP.setId(bookingDTO.getId());
        bookingSOAP.setWithBreakfast(bookingDTO.isWithBreakfast());
        bookingSOAP.setActive(bookingDTO.isActive());
        return bookingSOAP;
    }

}
