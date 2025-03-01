package business.bookingline;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.booking.Booking;
import business.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({
    @NamedQuery(name = "business.bookingLine.BookingLine.findByBookingIdAndRoomId", query = "SELECT b FROM BookingLine b WHERE b.id.bookingId = :bookingId AND b.id.roomId = :roomId"),
    @NamedQuery(name = "business.bookingLine.BookingLine.findByRoomId", query = "SELECT b FROM BookingLine b WHERE b.id.roomId = :roomId"),
})
public class BookingLine implements Serializable {

    @EmbeddedId
    private BookingLineId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private int numberOfNights;

    private double roomDailyPrice;

    private String startDate;

    private String endDate;

    @Column(columnDefinition = "boolean default true")
    private boolean available;
}
