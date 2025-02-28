package common.dto.soap.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BookingSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSOAP {
    @XmlElement
    private long id;
    @XmlElement
    private LocalDate date;
    @XmlElement
    private boolean withBreakfast;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private long customerId;
    @XmlElement
    private boolean available;
    @XmlElement
    private double totalPrice;

    // public static BookingSOAP toSOAP(BookingDTO bookingDTO) {
    //     BookingSOAP bookingSOAP = new BookingSOAP();
    //     bookingSOAP.setId(bookingDTO.getId());
    //     bookingSOAP.setCustomerId(bookingDTO.getCustomerId());
    //     bookingSOAP.setDate(bookingDTO.getDate());
    //     bookingSOAP.setNumberOfNights(bookingDTO.getNumberOfNights());
    //     bookingSOAP.setPeopleNumber(bookingDTO.getPeopleNumber());
    //     bookingSOAP.setId(bookingDTO.getId());
    //     bookingSOAP.setWithBreakfast(bookingDTO.isWithBreakfast());
    //     bookingSOAP.setAvailable(bookingDTO.isActive());
    //     return bookingSOAP;
    // }

}
