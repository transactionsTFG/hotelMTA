package business.hotel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import business.hotel.booking.BookingBO;
import business.hotel.booking.BookingDTO;
import business.hotel.customer.CustomerBO;
import business.hotel.room.RoomBO;
import business.hotel.room.RoomDTO;
import integration.SingletonEntityManager;

public class HotelASImp implements HotelAS {

	@Override
	public int createBooking(BookingDTO booking) {
		int res = SINTACTICAL_ERROR;
//		if (this.isValid(booking)) {
//			EntityManagerFactory emf = SingletonEntityManager.getInstance();
//			EntityManager em = emf.createEntityManager();
//			EntityTransaction et = em.getTransaction();
//			et.begin();
//			try {
//				CustomerBO customerBO = em.find(CustomerBO.class, booking.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
//				if (customerBO == null) {
//					res = NON_EXISTENT_CUSTOMER;
//					throw new Exception("Non existent customer");
//				}
//				
//				if (!customerBO.isActive()) {
//					res = NON_ACTIVE_CUSTOMER;
//					throw new Exception("Non active customer");
//				}
//				
//				TypedQuery<BookingBO> query = em.createNamedQuery("business.hotel.booking.BookingBO.findBydate", BookingBO.class);
//				query.setParameter("date", booking.getDate());
//				List<BookingBO> data = query.getResultList();
//				BookingBO bookingBO = data.isEmpty() ? null : data.get(0);
//			} catch (Exception e) {
//				res = UNEXPECTED_ERROR;
//				et.rollback();
//			} finally {
//				em.close();
//			}
//		}
		return res;
	}

	@Override
	public BookingDTO readBooking(int id) {
		BookingDTO booking = null;
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			BookingBO bookingBO = em.find(BookingBO.class, id, LockModeType.OPTIMISTIC);
			if (bookingBO == null) {
				et.rollback();
				throw new Exception("Booking does not exist");
			}
			
			booking = bookingBO.toTransfer();
			et.commit();
			
		} catch (Exception e) {
			booking = null;
		} finally {
			em.close();
		}
		return booking;
	}

	@Override
	public RoomDTO readRoom(int id) {
		RoomDTO room = null;
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			RoomBO roomBO = em.find(RoomBO.class, id, LockModeType.OPTIMISTIC);
			if (roomBO == null) {
				et.rollback();
				throw new Exception("Room does not exist");
			}
			
			room = roomBO.toTransfer();
			et.commit();
			
		} catch (Exception e) {
			room = null;
		} finally {
			em.close();
		}
		return room;
	}

	@Override
	public int updateBooking(BookingDTO booking) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBooking(int id) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		int res = NON_EXISTENT_BOOKING;
		try {
			
			BookingBO bookingBO = em.find(BookingBO.class, id);
			if (bookingBO == null) {
				res = NON_EXISTENT_BOOKING;
				et.rollback();
			} else {
				BookingDTO booking = bookingBO.toTransfer();
				if (booking.isActive()) {
					bookingBO.setActive(false);
					em.persist(bookingBO);
					res = bookingBO.getId();
					et.commit();
				} else {
					res = NON_ACTIVE_BOOKING;
					et.rollback();
				}
			}
			
		} catch(Exception e) {
			res = UNEXPECTED_ERROR;
		} finally {
			em.close();
		}
		return res;
	}
	
	private boolean isValid(BookingDTO booking) {
		return true;
	}

}
