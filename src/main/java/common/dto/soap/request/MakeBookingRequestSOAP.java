package common.dto.soap.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MakeBookingRequestSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
@ToString
public class MakeBookingRequestSOAP {

    @XmlElement
    private LocalDate startDate;
    @XmlElement
    private LocalDate endDate;
    @XmlElement
    private int numberOfNights;
    @XmlElement
    private boolean withBreakfast;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private int customerId;
    @XmlElement(name = "roomId")
    private List<Integer> roomIds;

}