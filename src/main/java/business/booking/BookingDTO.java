package business.booking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {

    private long id;
    private String date;
    private int numberOfNights;
    private boolean withBreakfast;
    private int peopleNumber;
    private int customerId;
    private boolean active;

    public BookingDTO() {
    }

    public BookingDTO(long id, String date, int numberOfNights, boolean withBreakfast,
            int peopleNumber, int customerId, boolean active) {
        super();
        this.id = id;
        this.date = date;
        this.numberOfNights = numberOfNights;
        this.withBreakfast = withBreakfast;
        this.peopleNumber = peopleNumber;
        this.customerId = customerId;
        this.active = active;
    }

    public BookingDTO(String date, int numberOfNights, boolean withBreakfast,
            int peopleNumber, int customerId) {
        super();
        this.date = date;
        this.numberOfNights = numberOfNights;
        this.withBreakfast = withBreakfast;
        this.peopleNumber = peopleNumber;
        this.customerId = customerId;
        this.active = true;
    }

}
