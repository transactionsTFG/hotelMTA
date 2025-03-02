package common.dto.soap.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean withBreakfast;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private long customerId;
    @XmlElement
    private boolean available;
    @XmlElement
    private double totalPrice;

}
