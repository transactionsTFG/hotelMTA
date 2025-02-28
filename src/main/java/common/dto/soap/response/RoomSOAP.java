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

    // public static RoomSOAP toSOAP(RoomDTO roomDTO) {
    //     RoomSOAP roomSOAP = new RoomSOAP();
    //     roomSOAP.setId(roomDTO.getId());
    //     roomSOAP.setNumber(roomDTO.getNumber());
    //     roomSOAP.setOccupied(roomDTO.isOccupied());
    //     roomSOAP.setSingleBed(roomDTO.isSingleBed());
    //     roomSOAP.setPeopleNumber(roomDTO.getPeopleNumber());
    //     roomSOAP.setAvailable(roomDTO.isAvailable());
    //     return roomSOAP;
    // }
}
