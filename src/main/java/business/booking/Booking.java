package business.booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import business.customer.Customer;
import business.room.BookingLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking implements Serializable {

    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Version
    private int version;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "booking")
    private List<BookingLine> bookingLines;

    @Column(columnDefinition = "boolean default false")
    private boolean withBreakfast;

    private int peopleNumber;

    @Column(columnDefinition = "boolean default true")
    private boolean available;

    private double totalPrice;

}
