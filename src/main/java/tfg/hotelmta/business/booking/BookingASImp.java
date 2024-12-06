package tfg.hotelmta.business.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;
import tfg.hotelmta.business.customer.Customer;
import tfg.hotelmta.business.exception.ExceptionAS;
import tfg.hotelmta.business.room.Room;
import tfg.hotelmta.business.room.RoomDTO;
import tfg.hotelmta.business.validators.Validator;
import tfg.hotelmta.integration.SingletonEntityManager;

public class BookingASImp implements BookingAS {

    @Override
    public int createBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        int res = -1;
        if (this.isValid(bookingDTO)) {
            EntityManagerFactory emf = SingletonEntityManager.getInstance();
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            try {
                Customer customer = em.find(Customer.class, bookingDTO.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                if (customer == null) {
                    res = -1;
                    throw new ExceptionAS("Customer with id " + bookingDTO.getCustomerId() + " does not exist");
                }

                if (!customer.isActive()) {
                    res = -1;
                    throw new ExceptionAS("Customer with id " + customer.getId() + " is not available");
                }

                int i = 0;
                boolean roomsOK = true;
                while (i < rooms.size() && roomsOK) {
                    RoomDTO roomDTO = rooms.get(i);
                    Room room = em.find(Room.class, roomDTO.getId(), LockModeType.OPTIMISTIC);
                    if (room == null) {
                        res = -1;
                        roomsOK = false;
                    }
                    if (roomsOK && !room.isActive()) {
                        res = -1;
                        roomsOK = false;
                    }
                    ++i;
                }

                if (!roomsOK) {
                    res = -1;
                    throw new ExceptionAS("At least one of the rooms does not exist or is not available");
                }

                Booking booking = new Booking(bookingDTO);
                booking.setCustomer(customer);
                List<Room> roomList = new ArrayList<>();
                for (RoomDTO r : rooms) {
                    roomList.add(new Room(r));
                }
                booking.setRoom(roomList);
                em.persist(booking);
                et.commit();
                res = booking.getId();
                bookingDTO.setId(res);

            } catch (Exception e) {
                if (!(e instanceof ExceptionAS)) {
                    res = -1;
                }
                et.rollback();
            } finally {
                em.close();
            }
        }
        return res;
    }

    @Override
    public int updateBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        int res = -1;
        if (this.isValid(bookingDTO)) {
            EntityManagerFactory emf = SingletonEntityManager.getInstance();
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            try {
                Booking booking = em.find(Booking.class, bookingDTO.getId());

                if (booking == null) {
                    res = -1;
                    throw new ExceptionAS("Booking with id " + bookingDTO.getId() + " does not exist");
                }

                Customer customer = em.find(Customer.class, bookingDTO.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                if (customer == null) {
                    res = -1;
                    throw new ExceptionAS("Customer with id " + bookingDTO.getCustomerId() + " does not exist");
                }

                if (!customer.isActive()) {
                    res = -1;
                    throw new ExceptionAS("Customer with id " + customer.getId() + " is not available");
                }

                int i = 0;
                boolean roomsOK = true;
                while (i < rooms.size() && roomsOK) {
                    RoomDTO roomDTO = rooms.get(i);
                    Room room = em.find(Room.class, roomDTO.getId(), LockModeType.OPTIMISTIC);
                    if (room == null) {
                        res = -1;
                        roomsOK = false;
                    }
                    if (roomsOK && !room.isActive()) {
                        res = -1;
                        roomsOK = false;
                    }
                    ++i;
                }

                if (!roomsOK) {
                    res = -1;
                    throw new ExceptionAS("At least one of the rooms does not exist or is not available");
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

                et.commit();
                res = booking.getId();

            } catch (Exception e) {
                if (!(e instanceof ExceptionAS)) {
                    res = -1;
                }
                et.rollback();
            } finally {
                em.close();
            }
        }
        return res;
    }

    @Override
    public int deleteBooking(int id) {
        int res = -1;
        EntityManagerFactory emf = SingletonEntityManager.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Booking booking = em.find(Booking.class, id);

            if (booking == null) {
                res = -1;
                throw new ExceptionAS("Booking with id " + id + " does not exist");
            }

            if (!booking.isActive()) {
                res = -1;
                throw new ExceptionAS("Booking with id " + id + " is not active");
            }

            Customer customer = booking.getCustomer();

            if (customer == null) {
                res = -1;
                throw new ExceptionAS("Customer with id " + booking.getCustomer().getId() + " does not exist");
            }

            if (!customer.isActive()) {
                res = -1;
                throw new ExceptionAS("Customer with id " + customer.getId() + " is not available");
            }

            for (Room r : booking.getRoom()) {
                r.setOccupied(false);
            }

            booking.setActive(false);

            et.commit();
            res = id;

        } catch (Exception e) {
            if (!(e instanceof ExceptionAS)) {
                res = -1;
            }
            et.rollback();
        } finally {
            em.close();
        }

        return res;
    }

    @Override
    public BookingTOA readBooking(int id) {
        BookingTOA bookingTOA = null;
        EntityManagerFactory emf = SingletonEntityManager.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Booking booking = em.find(Booking.class, id, LockModeType.OPTIMISTIC);
            if (booking == null) {
                et.rollback();
                throw new ExceptionAS("Booking with id " + id + " does not exist");
            }
            
            Customer customer = booking.getCustomer();
            
            if (customer == null) {
                throw new ExceptionAS("Customer with id " + booking.getCustomer().getId() + " does not exist");
            }
            
            em.lock(customer, LockModeType.OPTIMISTIC);
            
            List<RoomDTO> rooms = new ArrayList();
            
            for (Room r : booking.getRoom()) {
                em.lock(r, LockModeType.OPTIMISTIC);
                rooms.add(r.toTransfer());
            }
            bookingTOA = new BookingTOA(booking.toTransfer(), customer.toTransfer(), rooms);
            et.commit();

        } catch (Exception e) {
            et.rollback();
            bookingTOA = null;
        } finally {
            em.close();
        }
        return bookingTOA;
    }

    private boolean isValid(BookingDTO booking) {
        return booking != null
                && Validator.isDate(booking.getDate())
                && !booking.getAgencyName().isBlank()
                && !booking.getAgencyName().isEmpty()
                && booking.getCustomerId() > 0
                && booking.getPeopleNumber() > 0
                && booking.getNumberOfNights() > 0
                && !Validator.hasSQLInjection(booking.getAgencyName());
    }

}
