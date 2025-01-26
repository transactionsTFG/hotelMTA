package ucm.tfg.hotelMTA.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucm.tfg.hotelMTA.business.room.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    public Room findByNumber(int number);

}
