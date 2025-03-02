package business.room;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.List;

import business.bookingline.BookingLine;
import business.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NamedQueries({
        @NamedQuery(name = "business.room.getByRoomNumber", query = "SELECT r FROM Room r WHERE r.number = :number"),
        @NamedQuery(name = "business.room.getAllRoomsWithParams", query = "SELECT r, h.name, c.name FROM Room r JOIN r.hotel h JOIN h.countries c WHERE (:hotelName is NULL OR h.name = :hotelName) AND (:countryName is NULL OR c.name = :countryName)"),
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room implements Serializable {

    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Version
    private int version;

    @ManyToOne
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<BookingLine> bookingLines;

    private int number;

    private boolean singleBed;

    private boolean available;

    private int peopleNumber;

    private double dailyPrice;

}
