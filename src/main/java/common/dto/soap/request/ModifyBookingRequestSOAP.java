package common.dto.soap.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ModifyBookingRequestSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
public class ModifyBookingRequestSOAP {
    @XmlElement
    private long id;
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
    private long customerId;
    @XmlElement(name = "roomId")
    private List<Long> roomIds;
}