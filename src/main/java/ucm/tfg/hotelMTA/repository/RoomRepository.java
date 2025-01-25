package ucm.tfg.hotelMTA.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import tfg.ucm.hotelmta.Room;

@Component
public class RoomRepository {
    private static final Map<String, Room> rooms = new HashMap<>();

    @PostConstruct
    public void init() {
        Room room = new Room();
        room.setNumber(1);
        room.setOccupied(false);
        room.setSingleBed(true);
        room.setActive(true);
        room.setPeopleNumber(1);

        rooms.put("" + room.getNumber(), room);
    }

    public Room getRoom(int number) {
        return rooms.get("" + number);
    }
}
