package ucm.tfg.hotelMTA.business.booking;

import java.util.List;

import lombok.Data;
import ucm.tfg.hotelMTA.business.customer.CustomerDTO;
import ucm.tfg.hotelMTA.business.room.RoomDTO;

@Data
public class BookingTOA {

    private final BookingDTO booking;
    private final CustomerDTO customer;
    private final List<RoomDTO> rooms;

}