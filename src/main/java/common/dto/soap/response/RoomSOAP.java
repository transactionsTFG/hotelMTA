package common.dto.soap.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RoomSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomSOAP {
    @XmlElement
    private long id;
    @XmlElement
    private long hotelId;
    @XmlElement
    private int number;
    @XmlElement
    private boolean singleBed;
    @XmlElement
    private boolean available;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private double dailyPrice;

}
