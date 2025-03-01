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
        double totalPrice = booking.getTotalPrice();
        for (Long roomId : bookingSOAP.getRoomIds()) {
            Room room = em.find(Room.class, roomId, LockModeType.OPTIMISTIC);

            if (room == null) {
                throw new BookingASException(ASError.NON_EXISTENT_ROOM);
            }

            if (!room.isAvailable()) {
                throw new BookingASException(ASError.NON_ACTIVE_ROOM);
            }

            TypedQuery<BookingLine> query = em.createNamedQuery(
                    "business.bookingLine.BookingLine.findByBookingIdAndRoomId", BookingLine.class);
            query.setParameter("bookingId", booking.getId());
            query.setParameter("roomId", roomId);
            List<BookingLine> bookingLinesRoom = query.getResultList();
            System.out.println(
                    "hotelMTA.BookingASImpl.createBooking---------------------------------------------------- lineas de reserva para bookingId "
                            + booking.getId() + " y roomId " + roomId + ": " + bookingLinesRoom.size());

            if (bookingLinesRoom.isEmpty()) {

                TypedQuery<BookingLine> query2 = em.createNamedQuery(
                        "business.bookingLine.BookingLine.findByRoomId",
                        BookingLine.class);
                query2.setParameter("roomId", roomId);
                List<BookingLine> bookingLinesRoom2 = query2.getResultList();

                bookingLinesRoom2.forEach(b -> {
                    if (!b.isAvailable())
                        return;
                    DateValidator.validateDates(bookingSOAP.getStartDate(), bookingSOAP.getEndDate(), b.getStartDate(),
                            b.getEndDate());

                });

                System.out.println(
                        "hotelMTA.BookingASImpl.createBooking---------------------------------------------------- añadiendo nueva linea reserva para bookingId "
                                + booking.getId() + " y roomId " + roomId);
                BookingLine bookingLine = new BookingLine();
                bookingLine.setStartDate(bookingSOAP.getStartDate());
                bookingLine.setEndDate(bookingSOAP.getEndDate());
                bookingLine.setNumberOfNights(bookingSOAP.getNumberOfNights());
                bookingLine.setRoomDailyPrice(room.getDailyPrice());
                bookingLine.setAvailable(true);
                bookingLine.setRoom(room);
                bookingLine.setBooking(booking);
                em.persist(bookingLine);
                bookingLinesRoom.add(bookingLine);
                booking.getBookingLines().add(bookingLine);
                room.getBookingLines().add(bookingLine);
            } else {
                bookingLinesRoom.forEach(b -> {
                    if (b.getBooking().getId() == booking.getId() && b.getRoom().getId() == roomId) {
                        b.setStartDate(bookingSOAP.getStartDate());
                        b.setEndDate(bookingSOAP.getEndDate());
                        b.setNumberOfNights(bookingSOAP.getNumberOfNights());
                        b.setRoomDailyPrice(room.getDailyPrice());
                        b.setAvailable(true);
                    }
                });
            }

            // bookingLines.forEach(b -> {
            // if (!b.isAvailable())
            // return;
            // DateValidator.validateDates(bookingSOAP.getStartDate(),
            // bookingSOAP.getEndDate(), b.getStartDate(),
            // b.getEndDate());
            // });

            System.out
                    .println("hotelMTA.BookingASImpl.createBooking----------------------------------------------------"
                            + room.toString());

            // BookingLine bookingLine = new BookingLine();
            // bookingLine.setStartDate(bookingSOAP.getStartDate());
            // bookingLine.setEndDate(bookingSOAP.getEndDate());
            // bookingLine.setNumberOfNights(bookingSOAP.getNumberOfNights());
            // bookingLine.setRoomDailyPrice(room.getDailyPrice());
            // bookingLine.setAvailable(true);
            // bookingLine.setRoom(room);
            // bookingLine.setBooking(booking);
            // bookingLines.add(bookingLine);
            // em.persist(bookingLine);
            // room.getBookingLines().add(bookingLine);
            totalPrice += bookingSOAP.getNumberOfNights() * room.getDailyPrice();
        }

        booking.setWithBreakfast(bookingSOAP.isWithBreakfast());
        booking.setAvailable(true);
        booking.setCustomer(customer);
        booking.setBookingLines(bookingLines);
        booking.setPeopleNumber(bookingSOAP.getPeopleNumber());
        booking.setTotalPrice(totalPrice);

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

        final double totalPrice = booking.getTotalPrice();

        System.out.println(
                "hotelMTA.BookingASImpl.deleteBooking---------------------------------------------------- 'borrando' todas las booking lines de bookingId "
                        + booking.getId());
        booking.getBookingLines().stream().forEach(b -> {
            b.setAvailable(false);
        });

        booking.setAvailable(false);
        booking.setTotalPrice(0);

        return Result.success(totalPrice);

    }

    @Override
    public Result<Double> deleteBookingLine(long bookingId, long roomId) {

        Booking booking = em.find(Booking.class, bookingId);

        if (booking == null) {
            throw new BookingASException(ASError.BOOKING_NOT_FOUND);
        }

        if (!booking.isAvailable()) {
            throw new BookingASException(ASError.NON_ACTIVE_BOOKING);
        }

        Room room = em.find(Room.class, roomId);

        if (room == null) {
            throw new BookingASException(ASError.NON_EXISTENT_ROOM);
        }

        if (!room.isAvailable()) {
            throw new BookingASException(ASError.NON_ACTIVE_ROOM);
        }

        TypedQuery<BookingLine> query = em.createNamedQuery("business.bookingLine.BookingLine.findByBookingIdAndRoomId",
                BookingLine.class);
        query.setParameter("bookingId", bookingId);
        query.setParameter("roomId", roomId);
        List<BookingLine> bookingLines = query.getResultList();

        double totalPrice = booking.getTotalPrice();

        bookingLines.stream().forEach(b -> {
            if (!b.isAvailable())
                return;
            if (b.getBooking().getId() == bookingId && b.getRoom().getId() == roomId) {
                b.setAvailable(false);
                booking.setTotalPrice(booking.getTotalPrice() - b.getRoomDailyPrice() * b.getNumberOfNights());
            }
        });

        totalPrice -= booking.getTotalPrice();

        if (booking.getTotalPrice() <= 0) {
            booking.setTotalPrice(0);
            booking.setAvailable(false);
        }

        return Result.success(totalPrice);
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
