package business.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import business.customer.Customer;
import business.room.Room;
import business.room.RoomDTO;
import common.consts.ASError;
import common.dto.result.Result;
import common.dto.soap.request.MakeBookingRequestSOAP;
import common.dto.soap.request.ModifyBookingRequestSOAP;
import common.exception.BookingASException;
import common.validators.SOAPValidator;
import common.validators.Validator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

@Stateless
public class BookingASImp implements BookingAS {

    private EntityManager em;

    public BookingASImp() {
    }

    @Inject
    public BookingASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public Result<BookingTOA> createBooking(MakeBookingRequestSOAP bookingSOAP) {

        SOAPValidator.makeBookingRequestIsValid(bookingSOAP);

        if (bookingSOAP.getRoomIds() == null || bookingSOAP.getRoomIds().isEmpty()) {
            throw new BookingASException(ASError.INVALID_ROOM_LIST);
        }

        Customer customer = em.find(Customer.class, bookingSOAP.getCustomerId());

        if (customer == null) {
            throw new BookingASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isActive()) {
            throw new BookingASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        int i = 0;
        boolean roomsOK = true;
        List<Room> roomList = new ArrayList<>();
        while (i < bookingSOAP.getRoomIds().size() && roomsOK) {
            Integer roomId = bookingSOAP.getRoomIds().get(i);
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC);
            if (room == null) {
                roomsOK = false;
            } else {
                if (roomsOK && !room.isActive()) {
                    roomsOK = false;
                }
            }
            roomList.add(room);
            ++i;
        }

        if (!roomsOK) {
            throw new BookingASException(ASError.ROOM_LIST_HAS_NON_ACTIVE_OR_NON_EXISTENT_ROOMS);
        }

        Booking booking = new Booking();
        booking.setDate(bookingSOAP.getDate());
        booking.setNumberOfNights(bookingSOAP.getNumberOfNights());
        booking.setWithBreakfast(bookingSOAP.isWithBreakfast());
        booking.setActive(true);
        booking.setCustomer(customer);
        booking.setRoom(roomList);
        booking.setPeopleNumber(bookingSOAP.getPeopleNumber());

        em.persist(booking);
        em.flush();
        return Result.success(
                new BookingTOA(booking.toDTO(), customer.toDTO(), roomList.stream().map(Room::toDTO).toList()));

    }

    @Override
    public Result<BookingTOA> updateBooking(ModifyBookingRequestSOAP bookingSOAP) {
        SOAPValidator.modifyBookingRequestIsValid(bookingSOAP);

        Booking booking = em.find(Booking.class, bookingSOAP.getId());

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        if (bookingSOAP.getRoomIds() == null || bookingSOAP.getRoomIds().isEmpty()) {
            throw new BookingASException(ASError.INVALID_ROOM_LIST);
        }

        Customer customer = em.find(Customer.class, bookingSOAP.getCustomerId(),
                LockModeType.OPTIMISTIC_FORCE_INCREMENT);

        if (customer == null) {
            throw new BookingASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isActive()) {
            throw new BookingASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        int i = 0;
        boolean roomsOK = true;
        List<Room> roomList = new ArrayList<>();
        while (i < bookingSOAP.getRoomIds().size() && roomsOK) {
            Integer roomId = bookingSOAP.getRoomIds().get(i);
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC);
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

        booking.setDate(bookingSOAP.getDate());
        booking.setNumberOfNights(bookingSOAP.getNumberOfNights());
        booking.setWithBreakfast(bookingSOAP.isWithBreakfast());
        booking.setActive(true);
        booking.setCustomer(customer);
        booking.setRoom(roomList);
        booking.setPeopleNumber(bookingSOAP.getPeopleNumber());


        return Result.success(
                new BookingTOA(booking.toDTO(), customer.toDTO(), roomList.stream().map(Room::toDTO).toList()));

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

}
