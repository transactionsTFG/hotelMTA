package business.booking;

import java.util.List;
import business.customer.CustomerDTO;
import business.room.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingTOA {

    private BookingDTO booking;

    private CustomerDTO customer;

    private List<RoomDTO> rooms;

}
