package tfg.hotelmta.business.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import tfg.hotelmta.integration.SingletonEntityManager;

public class BookingASImp implements BookingAS {

    @Override
    public int createBooking(BookingDTO bookingDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int updateBooking(BookingDTO bookingDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
