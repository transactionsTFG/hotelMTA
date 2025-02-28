package business.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookingLineId implements Serializable {

    @Column(name = "booking_id")
    private long bookingId;

    @Column(name = "room_id")
    private long roomId;
}
