package ucm.tfg.hotelMTA.business.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingDTO {

    private int id;
    private String date;
    private int numberOfNights;
    private boolean withBreakfast;
    private String agencyName;
    private int peopleNumber;
    private int customerId;
    private boolean active;

}