package business.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import business.customer.Customer;
import business.room.Room;
import business.room.RoomDTO;
import common.consts.ASError;
import common.dto.result.Result;
import common.exception.BookingASException;
import common.validators.Validator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

@Stateless
public class BookingASImp implements BookingAS {

    private EntityManager em;

    public BookingASImp(){}

    @Inject
    public BookingASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public Result<BookingTOA> createBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {

        this.isValid(bookingDTO);

        if (rooms == null || rooms.isEmpty()) {
            throw new BookingASException(ASError.INVALID_ROOM_LIST);
        }

        Customer customer = em.find(Customer.class, bookingDTO.getCustomerId());

        if (customer == null) {
            throw new BookingASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isActive()) {
            throw new BookingASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        int i = 0;
        boolean roomsOK = true;
        while (i < rooms.size() && roomsOK) {
            RoomDTO roomDTO = rooms.get(i);
            Room room = em.find(Room.class, roomDTO.getId(), LockModeType.OPTIMISTIC);
            if (room == null) {
                roomsOK = false;
            } else {
                if (roomsOK && !room.isActive()) {
                    roomsOK = false;
                }
            }
            ++i;
        }

        if (!roomsOK) {
            throw new BookingASException(ASError.ROOM_LIST_HAS_NON_ACTIVE_OR_NON_EXISTENT_ROOMS);
        }

        Booking booking = new Booking(bookingDTO);
        booking.setCustomer(customer);
        List<Room> roomList = new ArrayList<>();
        for (RoomDTO r : rooms) {
            roomList.add(new Room(r));
        }
        booking.setRoom(roomList);

        em.persist(booking);

        return Result.success(new BookingTOA(booking.toDTO(), customer.toDTO(), rooms));

    }

    @Override
    public Result<BookingTOA> updateBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        this.isValid(bookingDTO);

        Booking booking = em.find(Booking.class, bookingDTO.getId());

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        if (rooms == null || rooms.isEmpty()) {
            throw new BookingASException(ASError.INVALID_ROOM_LIST);
        }

        Customer customer = em.find(Customer.class, bookingDTO.getCustomerId(),
                LockModeType.OPTIMISTIC_FORCE_INCREMENT);

        if (customer == null) {
            throw new BookingASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isActive()) {
            throw new BookingASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        int i = 0;
        boolean roomsOK = true;
        while (i < rooms.size() && roomsOK) {
            RoomDTO roomDTO = rooms.get(i);
            Room room = em.find(Room.class, roomDTO.getId(), LockModeType.OPTIMISTIC);
            if (room == null) {
                roomsOK = false;
            } else {
                if (roomsOK && !room.isActive()) {
                    roomsOK = false;
                }
            }
            ++i;
        }

        if (!roomsOK) {
            throw new BookingASException(ASError.ROOM_LIST_HAS_NON_ACTIVE_OR_NON_EXISTENT_ROOMS);
        }

        booking.setDate(bookingDTO.getDate());
        booking.setNumberOfNights(bookingDTO.getNumberOfNights());
        booking.setWithBreakfast(bookingDTO.isWithBreakfast());
        booking.setAgencyName(bookingDTO.getAgencyName());
        booking.setActive(true);
        booking.setCustomer(customer);
        List<Room> roomList = new ArrayList<>();
        for (RoomDTO r : rooms) {
            roomList.add(new Room(r));
        }
        booking.setRoom(roomList);

        em.persist(booking);

        return Result.success(new BookingTOA(booking.toDTO(), customer.toDTO(), rooms));

    }

    @Override
    public Result<Void> deleteBooking(int id) {

        Booking booking = em.find(Booking.class, id);

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        if (!booking.isActive()) {
            throw new BookingASException(ASError.NON_ACTIVE_BOOKING);
        }

        booking.setActive(false);

        for (Room r : booking.getRoom()) {
            em.lock(r, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            r.setOccupied(false);
            // booking.getRoom().remove(r);
        }

        em.persist(booking);

        return Result.success(null);

    }

    @Override
    public Result<BookingTOA> readBooking(int id) {

        Booking booking = em.find(Booking.class, id);

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        return Result.success(new BookingTOA(booking.toDTO(), booking.getCustomer().toDTO(),
                booking.getRoom().stream().map(Room::toDTO).collect(Collectors.toList())));

    }

    private void isValid(BookingDTO booking) {
        if (booking == null) {
            throw new BookingASException(ASError.NON_EXISTENT_BOOKING);
        }

        if (!Validator.isDate(booking.getDate())) {
            throw new BookingASException(ASError.INVALID_DATE);
        }

        if (!Validator.isName(booking.getAgencyName())) {
            throw new BookingASException(ASError.INVALID_AGENCY_NAME);
        }

        if (booking.getCustomerId() <= 0) {
            throw new BookingASException(ASError.INVALID_CUSTOMER_ID);
        }

        if (booking.getPeopleNumber() <= 0) {
            throw new BookingASException(ASError.INVALID_PEOPLE_NUMBER);
        }

        if (booking.getNumberOfNights() <= 0) {
            throw new BookingASException(ASError.INVALID_NUMBER_OF_NIGHTS);
        }

    }

}
