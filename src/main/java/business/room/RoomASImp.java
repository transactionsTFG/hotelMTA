package business.room;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import business.booking.Booking;
import business.booking.BookingDTO;
import business.bookingline.BookingLineDTO;
import common.consts.ASError;
import common.exception.RoomASException;
import common.mapper.BookingLineMapper;
import common.mapper.BookingMapper;
import common.mapper.RoomMapper;

@Stateless
public class RoomASImp implements RoomAS {

    private EntityManager em;

    public RoomASImp() {
    }

    @Inject
    public RoomASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public RoomDTO readRoom(long roomId) {
        Room room = this.em.find(Room.class, roomId, LockModeType.NONE);
        if (room == null)
            throw new RoomASException(ASError.ROOM_NOT_FOUND);

        return RoomMapper.INSTANCE.toDTO(room);

    }

    @Override
    public List<RoomParamsDTO> readRooms(String hotelName, String countryName) {
        TypedQuery<Object[]> query = this.em.createNamedQuery("business.room.getAllRoomsWithParams", Object[].class);
        query.setParameter("hotelName", hotelName != null ? hotelName : null);
        query.setParameter("countryName", countryName != null ? countryName : null);
        List<Object[]> results = query.getResultList();
        return results.stream()
                .map(result -> new RoomParamsDTO(
                        ((Room) result[0]).getId(),
                        ((Room) result[0]).getNumber(),
                        ((Room) result[0]).isSingleBed(),
                        ((Room) result[0]).isAvailable(),
                        ((Room) result[0]).getPeopleNumber(),
                        ((Room) result[0]).getDailyPrice(),
                        result[1] != null ? (String) result[1] : null,
                        result[2] != null ? (String) result[2] : null))
                .toList();
    }

    @Override
    public List<BookingLineDTO> readRoomsByBooking(long bookingID) {
        Booking booking = this.em.find(Booking.class, bookingID, LockModeType.OPTIMISTIC);

        if (booking == null)
            throw new RoomASException(ASError.BOOKING_NOT_FOUND);

        if (!booking.isAvailable())
            throw new RoomASException(ASError.NON_ACTIVE_BOOKING);

        return booking.getBookingLines().stream().map(b -> BookingLineMapper.INSTANCE.toDto(b,
                RoomMapper.INSTANCE.toDTO(b.getRoom()), BookingMapper.toDTO(b.getBooking()))).toList();
    }

}
