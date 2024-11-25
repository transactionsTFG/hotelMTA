package business.hotel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		if (this.isValid(booking)) {
			EntityManagerFactory emf = SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				CustomerBO customerBO = em.find(CustomerBO.class, booking.getCustomerId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (customerBO == null) {
					res = NON_EXISTENT_CUSTOMER;
					throw new Exception("Non existent customer");
				}
				
				if (!customerBO.isActive()) {
					res = NON_ACTIVE_CUSTOMER;
					throw new Exception("Non active customer");
				}
				
				TypedQuery<BookingBO> query = em.createNamedQuery("business.hotel.booking.BookingBO.findBydate", BookingBO.class);
				query.setParameter("date", booking.getDate());
				List<BookingBO> data = query.getResultList();
				BookingBO bookingBO = data.isEmpty() ? null : data.get(0);

				if (data.isEmpty()) {
					bookingBO = new BookingBO(booking);
					bookingBO.setCustomerBO(customerBO);
					em.persist(bookingBO);
					et.commit();
					res = bookingBO.getId();
					booking.setId(res);
				} else {
					boolean isAbleToBook = true;
					int i = 0;
					while (i < data.size() && isAbleToBook) {
						BookingBO bookingItem = data.get(i);
						if (this.getEndDate1(bookingItem.getDate(), bookingItem.getNumberOfNights()) > bookingBO.getDate()) {
							
						}
						++i;
					}
				}
				
			} catch (Exception e) {
				res = UNEXPECTED_ERROR;
				et.rollback();
			} finally {
				em.close();
			}
		}
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

	private List<Integer> getIntegerDate(String date) {
		// dd-mm-yyyy
		String daystr = date.substring(0, 2);
		String monthstr = date.substring(3, 5);
		String yearstr = date.substring(6);
		int day = Integer.parseInt(daystr);
		int month = Integer.parseInt(monthstr);
		int year = Integer.parseInt(yearstr);
		List<Integer> ret = new ArrayList<Integer>();
		ret.add(day);
		ret.add(month);
		ret.add(year);
		return ret;
	}
	
	private static Date getEndDate1(String strDate, int numberOfNights) {
		List<Integer> integerDate = this.getIntegerDate(strDate);
		
		int day = integerDate.get(0);
		int month = integerDate.get(1);
		int year = integerDate.get(2);
		
		Date date = new Date(year, month, day);
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.add(Calendar.DATE, numberOfNights);
		
		return new Date(calDate.YEAR, calDate.MONTH, calDate.DATE);
	}
	
	
	private String getEndDate(String date, int numberOfNights) {
		String daystr = date.substring(0, 2);
		String monthstr = date.substring(3, 5);
		String yearstr = date.substring(6);
		
		List<Integer> integerDate = this.getIntegerDate(date);
		
		int day = integerDate.get(0);
		int month = integerDate.get(1);
		int year = integerDate.get(2);
		
		
		int dayEnd = day + numberOfNights;
		int monthEnd = month;
		int yearEnd = year;
		
		int daysInMonth = getDays(month);
		if (dayEnd > daysInMonth) {
			dayEnd -= daysInMonth;
			monthEnd++;
		}
		
		if (monthEnd > 12) {
			monthEnd -= 12;
			yearEnd++;
		}
		
		daystr = (dayEnd < 10 ? "0" : "") + dayEnd;
		monthstr = (monthEnd < 10 ? "0" : "") + monthEnd;
		yearstr = "" + yearEnd;
		
		return daystr + "-" + monthstr + "-" + yearstr;
	}
	
	// Returns true if date1 is greater than date2
	private boolean isGreater(String date1, String date2) {
		List<Integer> integerDate1 = this.getIntegerDate(date1);
		List<Integer> integerDate2 = this.getIntegerDate(date2);
		
		int day1 = integerDate1.get(0);
		int month1 = integerDate1.get(1);
		int year1 = integerDate1.get(2);

		int day2 = integerDate2.get(0);
		int month2 = integerDate2.get(1);
		int year2 = integerDate2.get(2);
		
		// 01-01-2024, 02-01-2024
		
		if (year1 > year2) return true;
		
		
		
		if (year1 <= year2) {
			if (month1 <= month2) {
				if (day1 < day2) {
					return true;
				}
			}
		}
		return false;
		
		
	}
	
	private int getDays(int month) {
		switch(month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			default:
			return 28;
		}
	}
	
	public static void main (String[] args) {
		System.out.println(getEndDate1("01-01-2024", 3));
	}

}
