package tfg.hotelmta.business.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import tfg.hotelmta.business.customer.Customer;
import tfg.hotelmta.business.validators.Validator;
import tfg.hotelmta.integration.SingletonEntityManager;

public class BookingASImp implements BookingAS {

    @Override
    public int createBooking(BookingDTO bookingDTO) {
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
                    throw new Exception("Non existent customer");
                }

                if (!customer.isActive()) {
                    res = -1;
                    throw new Exception("Non active customer");
                }

                Booking booking = new Booking(bookingDTO);
                booking.setCustomer(customer);
                em.persist(booking);
                et.commit();
                res = booking.getId();
                bookingDTO.setId(res);

            } catch (Exception e) {
                et.rollback();
            } finally {
                em.close();
            }
        }
        return res;
    }

    @Override
    public int updateBooking(BookingDTO bookingDTO) {
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
                    throw new Exception("Booking with id " + bookingDTO.getId() + " does not exist");
                }
                
                Customer customer = em.find(Customer.class, bookingDTO.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                if (customer == null) {
                    res = -1;
                    throw new Exception("Customer with id " + bookingDTO.getCustomerId() + " does not exist");
                }

                if (!customer.isActive()) {
                    res = -1;
                    throw new Exception("Customer with id " + bookingDTO.getCustomerId() + " is not active");
                }
                
                // Read rooms

                booking.setCustomer(customer);
                booking.setActive(true);
                booking.setAgencyName(bookingDTO.getAgencyName());
                booking.setDate(bookingDTO.getDate());
                booking.setNumberOfNights(bookingDTO.getNumberOfNights());
                booking.setPeopleNumber(bookingDTO.getPeopleNumber());
                booking.setWithBreakfast(bookingDTO.isWithBreakfast());
                et.commit();
                res = booking.getId();
                bookingDTO.setId(res);

            } catch (Exception e) {
                et.rollback();
            } finally {
                em.close();
            }
        }
        return res;
    }

    @Override
    public int deleteBooking(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BookingDTO readBooking(int id) {
        BookingDTO bookingDTO = null;
        EntityManagerFactory emf = SingletonEntityManager.getInstance();
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Booking booking = em.find(Booking.class, id, LockModeType.OPTIMISTIC);
            if (booking == null) {
                et.rollback();
                throw new Exception("Booking with id " + id + " does not exist");
            }

            bookingDTO = booking.toTransfer();
            et.commit();

        } catch (Exception e) {
            bookingDTO = null;
        } finally {
            em.close();
        }
        return bookingDTO;
    }

    private boolean isValid(BookingDTO booking) {
        return booking != null
                && Validator.isDate(booking.getDate())
                && !booking.getAgencyName().isBlank()
                && !booking.getAgencyName().isEmpty()
                && booking.getCustomerId() > 0
                && booking.getPeopleNumber() > 0
                && !Validator.hasSQLInjection(booking.getAgencyName());
    }

}
