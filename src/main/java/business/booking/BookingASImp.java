package business.booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.bookingline.BookingLine;
import business.customer.Customer;
import business.room.Room;
import common.consts.ASError;
import common.dto.result.Result;
import common.dto.soap.request.MakeBookingRequestSOAP;
import common.dto.soap.request.ModifyBookingRequestSOAP;
import common.exception.BookingASException;
import common.mapper.BookingMapper;
import common.mapper.CustomerMapper;
import common.mapper.RoomMapper;
import common.validators.DateValidator;
import common.validators.SOAPValidator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

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
        em.persist(booking);
        double totalPrice = 0;
        List<BookingLine> bookingLines = new ArrayList<>();
        while (i < bookingSOAP.getRoomIds().size() && roomsOK) {
            Long roomId = bookingSOAP.getRoomIds().get(i);
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            if (room == null) {
                throw new BookingASException(ASError.NON_EXISTENT_ROOM);
            }

            if (!room.isAvailable()) {
                throw new BookingASException(ASError.NON_ACTIVE_ROOM);
            }

            TypedQuery<BookingLine> query = em.createNamedQuery(
                    "business.bookingLine.BookingLine.findByRoomId",
                    BookingLine.class);
            query.setParameter("roomId", roomId);
            List<BookingLine> bookingLinesRoom = query.getResultList();

            bookingLinesRoom.forEach(b -> {
                if (!b.isAvailable())
                    return;
                DateValidator.validateDates(bookingSOAP.getStartDate(), bookingSOAP.getEndDate(), b.getStartDate(),
                        b.getEndDate());

            });

            System.out
                    .println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                            + room.toString());
            room.getBookingLines().forEach(b -> {
                if (b.getStartDate().compareTo(bookingSOAP.getStartDate()) <= 0
                        && b.getEndDate().compareTo(bookingSOAP.getEndDate()) >= 0) {
                    throw new BookingASException(ASError.ROOM_ALREADY_BOOKED);
                }
            });

            System.out
                    .println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                            + room.toString());

            BookingLine bookingLine = new BookingLine();
            bookingLine.setStartDate(bookingSOAP.getStartDate());
            bookingLine.setEndDate(bookingSOAP.getEndDate());
            bookingLine.setNumberOfNights(bookingSOAP.getNumberOfNights());
            bookingLine.setRoomDailyPrice(room.getDailyPrice());
            bookingLine.setAvailable(true);
            bookingLine.setRoom(room);
            bookingLine.setBooking(booking);
            bookingLines.add(bookingLine);
            em.persist(bookingLine);
            room.getBookingLines().add(bookingLine);
            totalPrice += room.getDailyPrice() * bookingSOAP.getNumberOfNights();
            System.out.println(
                    "hotelMTA.BookingASImpl.createBooking---------------------------------------------------- despues de persistir bookingLine");
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
        booking.setTotalPrice(totalPrice);

        // em.persist(booking);
        System.out.println(
                "hotelMTA.BookingASImpl.createBooking---------------------------------------------------- despues de persistir booking");
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

        List<BookingLine> bookingLines = booking.getBookingLines();
        for (Long roomId : bookingSOAP.getRoomIds()) {
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC);

            if (room == null) {
                throw new BookingASException(ASError.NON_EXISTENT_ROOM);
            }

            if (!room.isAvailable()) {
                throw new BookingASException(ASError.NON_ACTIVE_ROOM);
            }

            TypedQuery<BookingLine> query = em.createNamedQuery(
                    "business.bookingLine.BookingLine.findByRoomId",
                    BookingLine.class);
            query.setParameter("roomId", roomId);
            List<BookingLine> bookingLinesRoom = query.getResultList();

            bookingLinesRoom.forEach(b -> {
                if (b.isAvailable() && b.getStartDate().compareTo(bookingSOAP.getStartDate()) <= 0
                        && b.getEndDate().compareTo(bookingSOAP.getEndDate()) >= 0) {
                    throw new BookingASException(ASError.ROOM_ALREADY_BOOKED);
                }
            });

            // room.getBookingLines().forEach(b -> {
            // if (b.getStartDate().compareTo(bookingSOAP.getStartDate()) <= 0
            // && b.getEndDate().compareTo(bookingSOAP.getEndDate()) >= 0) {
            // throw new BookingASException(ASError.ROOM_ALREADY_BOOKED);
            // }
            // });

            System.out
                    .println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                            + room.toString());

            BookingLine bookingLine = new BookingLine();
            bookingLine.setStartDate(bookingSOAP.getStartDate());
            bookingLine.setEndDate(bookingSOAP.getEndDate());
            bookingLine.setNumberOfNights(bookingSOAP.getNumberOfNights());
            bookingLine.setRoomDailyPrice(room.getDailyPrice());
            bookingLine.setAvailable(true);
            bookingLine.setRoom(room);
            bookingLine.setBooking(booking);
            bookingLines.add(bookingLine);
            em.persist(bookingLine);
            room.getBookingLines().add(bookingLine);
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
