package ucm.tfg.hotelMTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tfg.ucm.hotelmta.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    public Room findByNumber(int number);

}
