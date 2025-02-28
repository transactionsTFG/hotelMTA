package business.booking;

import java.util.ArrayList;
import java.util.List;
import business.customer.Customer;
import business.room.BookingLine;
import business.room.Room;
import common.consts.ASError;
import common.dto.result.Result;
import common.dto.soap.request.MakeBookingRequestSOAP;
import common.dto.soap.request.ModifyBookingRequestSOAP;
import common.exception.BookingASException;
import common.mapper.BookingMapper;
import common.mapper.CustomerMapper;
import common.mapper.RoomMapper;
import common.validators.SOAPValidator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

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

        System.out.println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                + bookingSOAP.toString());
        SOAPValidator.makeBookingRequestIsValid(bookingSOAP);

        // if (bookingSOAP.getRoomIds() == null || bookingSOAP.getRoomIds().isEmpty()) {
        // throw new BookingASException(ASError.INVALID_ROOM_LIST);
        // }

        Customer customer = em.find(Customer.class, bookingSOAP.getCustomerId());

        if (customer == null) {
            throw new BookingASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isAvailable()) {
            throw new BookingASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        int i = 0;
        boolean roomsOK = true;
        Booking booking = new Booking();
        List<BookingLine> bookingLines = new ArrayList<>();
        while (i < bookingSOAP.getRoomIds().size() && roomsOK) {
            Integer roomId = bookingSOAP.getRoomIds().get(i);
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC);

            if (room == null) {
                throw new BookingASException(ASError.NON_EXISTENT_ROOM);
            }

            if (!room.isAvailable()) {
                throw new BookingASException(ASError.NON_ACTIVE_ROOM);
            }

            System.out
                    .println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                            + room.toString());

            BookingLine bookingLine = new BookingLine();
            bookingLine.setStartDate(bookingSOAP.getStartDate());
            bookingLine.setEndDate(bookingSOAP.getEndDate());
            bookingLine.setNumberOfNights(bookingSOAP.getNumberOfNights());
            bookingLine.setRoomDailyPrice(room.getDailyPrice());
            bookingLine.setRoom(room);
            bookingLine.setBooking(booking);
            bookingLines.add(bookingLine);
            room.getBookingLines().add(bookingLine);
            ++i;
        }

        if (!roomsOK) {
            throw new BookingASException(ASError.ROOM_LIST_HAS_NON_ACTIVE_OR_NON_EXISTENT_ROOMS);
        }

        booking.setWithBreakfast(bookingSOAP.isWithBreakfast());
        booking.setAvailable(true);
        booking.setCustomer(customer);
        booking.setBookingLines(bookingLines);
        booking.setPeopleNumber(bookingSOAP.getPeopleNumber());

        em.persist(booking);
        em.flush();
        return Result.success(
                new BookingTOA(BookingMapper.toDTO(booking),
                        CustomerMapper.toDTO(customer),
                        bookingLines.stream().map(b -> RoomMapper.toDTO(b.getRoom())).toList()));

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

        Customer customer = em.find(Customer.class, bookingSOAP.getCustomerId());

        if (customer == null) {
            throw new BookingASException(ASError.NON_EXISTENT_CUSTOMER);
        }

        if (!customer.isAvailable()) {
            throw new BookingASException(ASError.NON_ACTIVE_CUSTOMER);
        }

        int i = 0;
        boolean roomsOK = true;
        List<BookingLine> bookingLines = new ArrayList<>();
        while (i < bookingSOAP.getRoomIds().size() && roomsOK) {
            Integer roomId = bookingSOAP.getRoomIds().get(i);
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC);

            if (room == null) {
                throw new BookingASException(ASError.NON_EXISTENT_ROOM);
            }

            if (!room.isAvailable()) {
                throw new BookingASException(ASError.NON_ACTIVE_ROOM);
            }

            System.out
                    .println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                            + room.toString());

            BookingLine bookingLine = new BookingLine();
            bookingLine.setStartDate(bookingSOAP.getStartDate());
            bookingLine.setEndDate(bookingSOAP.getEndDate());
            bookingLine.setNumberOfNights(bookingSOAP.getNumberOfNights());
            bookingLine.setRoomDailyPrice(room.getDailyPrice());
            bookingLine.setRoom(room);
            bookingLine.setBooking(booking);
            bookingLines.add(bookingLine);
            room.getBookingLines().add(bookingLine);
            ++i;
        }

        if (!roomsOK) {
            throw new BookingASException(ASError.ROOM_LIST_HAS_NON_ACTIVE_OR_NON_EXISTENT_ROOMS);
        }

        booking.setWithBreakfast(bookingSOAP.isWithBreakfast());
        booking.setAvailable(true);
        booking.setCustomer(customer);
        booking.setBookingLines(bookingLines);
        booking.setPeopleNumber(bookingSOAP.getPeopleNumber());

        em.persist(booking);
        em.flush();
        return Result.success(
                new BookingTOA(BookingMapper.toDTO(booking),
                        CustomerMapper.toDTO(customer),
                        bookingLines.stream().map(b -> RoomMapper.toDTO(b.getRoom())).toList()));

    }

    @Override
    public Result<Double> deleteBooking(long id) {

        Booking booking = em.find(Booking.class, id);

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        if (!booking.isAvailable()) {
            throw new BookingASException(ASError.NON_ACTIVE_BOOKING);
        }

        booking.setAvailable(false);

        // for (Room r : booking.getRooms()) {
        // em.lock(r, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        // r.setOccupied(false);
        // booking.getRoom().remove(r);
        // }

        em.persist(booking);

        return Result.success(booking.getTotalPrice());

    }

    @Override
    public Result<BookingTOA> readBooking(long id) {

        Booking booking = em.find(Booking.class, id);

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        return Result.success(
                new BookingTOA(BookingMapper.toDTO(booking),
                        CustomerMapper.toDTO(booking.getCustomer()),
                        booking.getBookingLines().stream().map(b -> RoomMapper.toDTO(b.getRoom())).toList()));

    }

}
