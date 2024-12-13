package business.room;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;
import business.booking.Booking;

@Entity
@NamedQueries({
        @NamedQuery(name = "business.room.getByRoomNumber", query = "SELECT r FROM Room r WHERE r.number = :number")
})
public class Room implements Serializable {

    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Version
    private int version;
    private int number;
    private boolean occupied;
    private boolean singleBed;
    private boolean active;
    @ManyToMany(mappedBy = "room")
    private List<Booking> booking;
    private int peopleNumber;

    public Room() {
    }

    public Room(RoomDTO roomDTO) {
        this.setId(roomDTO.getId());
        this.setNumber(roomDTO.getNumber());
        this.setOccupied(roomDTO.isOccupied());
        this.setSingleBed(roomDTO.isSingleBed());
        this.setActive(roomDTO.isActive());
        this.setPeopleNumber(roomDTO.getPeopleNumber());
    }

    public Room(int number, boolean occupied, boolean singleBed, boolean active, int peopleNumber) {
        this.number = number;
        this.occupied = occupied;
        this.singleBed = singleBed;
        this.active = active;
        this.peopleNumber = peopleNumber;
    }

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

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public RoomDTO toDTO() {
        return new RoomDTO(id, number, occupied, singleBed, active, peopleNumber);
    }

}
