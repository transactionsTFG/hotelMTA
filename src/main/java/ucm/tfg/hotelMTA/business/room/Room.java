package ucm.tfg.hotelMTA.business.room;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import ucm.tfg.hotelMTA.business.booking.Booking;

@AllArgsConstructor
@Data
@Entity
public class Room implements Serializable {

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

    public Room(RoomDTO roomDTO) {
        this.id = roomDTO.getId();
        this.number = roomDTO.getNumber();
        this.occupied = roomDTO.isOccupied();
        this.singleBed = roomDTO.isSingleBed();
        this.active = roomDTO.isActive();
        this.peopleNumber = roomDTO.getPeopleNumber();
    }

    public RoomDTO toDTO() {
        return new RoomDTO(id, number, occupied, singleBed, active, peopleNumber);
    }
}
