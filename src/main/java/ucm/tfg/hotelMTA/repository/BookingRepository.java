package ucm.tfg.hotelMTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfg.ucm.hotelmta.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    public Booking findById(int id);
}
