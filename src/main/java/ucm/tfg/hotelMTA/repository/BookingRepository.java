package ucm.tfg.hotelMTA.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import tfg.ucm.hotelmta.Booking;
import tfg.ucm.hotelmta.Room;

@Component
public class BookingRepository {
    private static final Map<Integer, Booking> bookings = new HashMap<>();

    @PostConstruct
    public void init() {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setActive(true);
        booking.setDate("01-01-2025");
        booking.setNightsNumber(1);
        booking.setWithBreakfast(true);
        booking.setAgencyName("Hilton");
        booking.setCustomerDNI("12345678A");
        booking.setPeopleNumber(1);
        Room room = new Room();
        room.setNumber(1);
        room.setOccupied(false);
        room.setSingleBed(true);
        room.setActive(true);
        room.setPeopleNumber(1);
        Booking.Rooms rooms = new Booking.Rooms();
        rooms.getRoomNumber().add(room.getNumber());
        booking.setRooms(rooms);
        bookings.put(booking.getId(), booking);
    }

    public Booking getBooking(int number) {
        return bookings.get(number);
    }
}
