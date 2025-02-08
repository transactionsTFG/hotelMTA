package common.dto.soap.response;

import business.room.RoomDTO;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RoomSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomSOAP {
    @XmlElement
    private int id;
    @XmlElement
    private int number;
    @XmlElement
    private boolean occupied;
    @XmlElement
    private boolean singleBed;
    @XmlElement
    private boolean active;
    @XmlElement
    private int peopleNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isSingleBed() {
        return singleBed;
    }

    public void setSingleBed(boolean singleBed) {
        this.singleBed = singleBed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public static RoomSOAP toSOAP(RoomDTO roomDTO) {
        RoomSOAP roomSOAP = new RoomSOAP();
        roomSOAP.setId(roomDTO.getId());
        roomSOAP.setNumber(roomDTO.getNumber());
        roomSOAP.setOccupied(roomDTO.isOccupied());
        roomSOAP.setSingleBed(roomDTO.isSingleBed());
        roomSOAP.setPeopleNumber(roomDTO.getPeopleNumber());
        roomSOAP.setActive(roomDTO.isActive());
        return roomSOAP;
    }
}
