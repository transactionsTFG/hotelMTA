package ucm.tfg.hotelMTA;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import ucm.tfg.hotelMTA.business.room.Room;
import ucm.tfg.hotelMTA.business.room.RoomDTO;
import ucm.tfg.hotelMTA.repository.RoomRepository;

@SpringBootTest
public class HotelMTAApplicationTests {

    private RoomRepository roomRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testing() {
        RoomDTO room = new RoomDTO(0, 1, true, true, true, 1);
        roomRepository.saveAndFlush(room);
    }

}
