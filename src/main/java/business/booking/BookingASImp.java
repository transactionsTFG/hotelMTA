package business.booking;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import business.customer.Customer;
import business.room.Room;
import business.room.RoomDTO;
import business.utils.ErrorResponses;
import integration.transaction.Transaction;
import integration.transaction.TransactionManager;

public class BookingASImp implements BookingAS {

    private final EntityManager em;

    @Inject
    public BookingASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public int createBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        int res = ErrorResponses.SYNTAX_ERROR;
        if (!this.isValid(bookingDTO)) {
            return res;
        }
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {

            Customer customer = em.find(Customer.class, bookingDTO.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            if (customer == null) {
                res = ErrorResponses.NON_EXISTENT_CUSTOMER;
                throw new ASException("Customer with id " + bookingDTO.getCustomerId() + " does not exist");
            }

            if (!customer.isActive()) {
                res = ErrorResponses.NON_ACTIVE_CUSTOMER;
                throw new ASException("Customer with id " + customer.getId() + " is not available");
            }

            int i = 0;
            boolean roomsOK = true;
            while (i < rooms.size() && roomsOK) {
                RoomDTO roomDTO = rooms.get(i);
                Room room = em.find(Room.class, roomDTO.getId(), LockModeType.OPTIMISTIC);
                if (room == null) {
                    res = ErrorResponses.NON_EXISTENT_ROOM;
                    roomsOK = false;
                } else {
                    if (roomsOK && !room.isActive()) {
                        res = ErrorResponses.NON_ACTIVE_ROOM;
                        roomsOK = false;
                    }
                }
                ++i;
            }

            if (!roomsOK) {
                throw new ASException("At least one of the rooms does not exist or is not available");
            }

            Booking booking = new Booking(bookingDTO);
            booking.setCustomer(customer);
            List<Room> roomList = new ArrayList<>();
            for (RoomDTO r : rooms) {
                roomList.add(new Room(r));
            }
            booking.setRoom(roomList);
            em.persist(booking);
            t.commit();
            res = booking.getId();
            bookingDTO.setId(res);

        } catch (Exception e) {
            if (!(e instanceof ASException)) {
                res = ErrorResponses.UNEXPECTED_ERROR;
            }
            t.rollback();
        }

        return res;
    }

    @Override
    @Transactional
    public int updateBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        int res = ErrorResponses.SYNTAX_ERROR;
        if (!this.isValid(bookingDTO)) {
            return res;
        }
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Booking booking = em.find(Booking.class, bookingDTO.getId());

            if (booking == null) {
                res = ErrorResponses.NON_EXISTENT_BOOKING;
                throw new ASException("Booking with id " + bookingDTO.getId() + " does not exist");
            }

            Customer customer = em.find(Customer.class, bookingDTO.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            if (customer == null) {
                res = ErrorResponses.NON_EXISTENT_CUSTOMER;
                throw new ASException("Customer with id " + bookingDTO.getCustomerId() + " does not exist");
            }

            if (!customer.isActive()) {
                res = ErrorResponses.NON_ACTIVE_CUSTOMER;
                throw new ASException("Customer with id " + customer.getId() + " is not available");
            }

            int i = 0;
            boolean roomsOK = true;
            while (i < rooms.size() && roomsOK) {
                RoomDTO roomDTO = rooms.get(i);
                Room room = em.find(Room.class, roomDTO.getId(), LockModeType.OPTIMISTIC);
                if (room == null) {
                    res = ErrorResponses.NON_EXISTENT_ROOM;
                    roomsOK = false;
                } else {
                    if (roomsOK && !room.isActive()) {
                        res = ErrorResponses.NON_ACTIVE_ROOM;
                        roomsOK = false;
                    }
                }
                ++i;
            }

            if (!roomsOK) {
                throw new ASException("At least one of the rooms does not exist or is not available");
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
            booking.setPeopleNumber(bookingDTO.getPeopleNumber());

            t.commit();
            res = booking.getId();

        } catch (Exception e) {
            if (!(e instanceof ASException)) {
                res = ErrorResponses.UNEXPECTED_ERROR;
            }
            t.rollback();
        }
        return res;
    }

    @Override
    @Transactional
    public int deleteBooking(int id) {
        int res = ErrorResponses.NON_EXISTENT_BOOKING;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Booking booking = em.find(Booking.class, id);

            if (booking == null) {
                throw new ASException("Booking with id " + id + " does not exist");
            }

            if (!booking.isActive()) {
                res = ErrorResponses.NON_ACTIVE_BOOKING;
                throw new ASException("Booking with id " + id + " is not active");
            }

            Customer customer = booking.getCustomer();

            if (customer == null) {
                res = ErrorResponses.NON_EXISTENT_CUSTOMER;
                throw new ASException("Customer with id " + booking.getCustomer().getId() + " does not exist");
            }

            if (!customer.isActive()) {
                res = ErrorResponses.NON_ACTIVE_CUSTOMER;
                throw new ASException("Customer with id " + customer.getId() + " is not available");
            }

            for (Room r : booking.getRoom()) {
                r.setOccupied(false);
            }

            booking.setActive(false);

            t.commit();
            res = id;

        } catch (Exception e) {
            if (!(e instanceof ASException)) {
                res = ErrorResponses.UNEXPECTED_ERROR;
            }
            t.rollback();
        }

        return res;
    }

    @Override
    @Transactional
    public BookingTOA readBooking(int id) {
        BookingTOA bookingTOA = null;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Booking booking = em.find(Booking.class, id, LockModeType.OPTIMISTIC);
            if (booking == null) {
                throw new ASException("Booking with id " + id + " does not exist");
            }

            Customer customer = booking.getCustomer();

            if (customer == null) {
                throw new ASException("Customer with id " + booking.getCustomer().getId() + " does not exist");
            }

            em.lock(customer, LockModeType.OPTIMISTIC);

            List<RoomDTO> rooms = new ArrayList<>();

            for (Room r : booking.getRoom()) {
                em.lock(r, LockModeType.OPTIMISTIC);
                rooms.add(r.toDTO());
            }
            bookingTOA = new BookingTOA(booking.toDTO(), customer.toDTO(), rooms);
            t.commit();

        } catch (Exception e) {
            t.rollback();
            bookingTOA = null;
        }
        return bookingTOA;
    }

    private boolean isValid(BookingDTO booking) {
        return true;
        /*
        return booking != null
        && Validator.isDate(booking.getDate())
        && !booking.getAgencyName().isBlank()
        && !booking.getAgencyName().isEmpty()
        && booking.getCustomerId() > 0
        && booking.getPeopleNumber() > 0
        && booking.getNumberOfNights() > 0
        && !Validator.hasSQLInjection(booking.getAgencyName());
         */
    }

}
