package common.dto.soap.request;

import java.time.LocalDate;
import java.util.Date;
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
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private int numberOfNights;
    @XmlElement
    private boolean withBreakfast;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private String customerDNI;
    @XmlElement(name = "roomId")
    private List<Long> roomIds;

    @XmlElement
    private UserSOAP user;

}