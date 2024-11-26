package business.hotel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import business.BusinessFactory;
import business.hotel.booking.BookingDTO;
import business.hotel.customer.CustomerAS;
import business.hotel.customer.CustomerDTO;
import business.hotel.room.RoomAS;
import business.hotel.room.RoomDTO;

public class HotelASImpTest {
	
	private static final int numberOfNights = 2,
							 peopleNumber = 2,
							 INF = 999999999;
	private static final boolean withBreakfast = true;
	private static final String date = "31-12-2024", 
								agencyName = "UCM";
	
	private static final String name = "Juan",
								email = "juan@gmail.com",
								phone = "123456789",
								dni = "12345678A";
	
	private static final int number = 345;
	private static final boolean occupied = false,
								 singleBed = true,
								 active = true;
	
	private HotelAS hotelAS;
	private CustomerAS customerAS;
	private RoomAS roomAS;
	private CustomerDTO customer;
	private BookingDTO booking;
	private RoomDTO room;
	
	@Before public void setUp() {
		this.hotelAS = BusinessFactory.getInstance().createHotelAS();
	}
	
	@Test public void createBookingKONonExistentCustomer() {
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, INF);
		assertEquals(this.hotelAS.createBooking(booking), HotelAS.NON_EXISTENT_CUSTOMER);
	}
	@Test public void createBookingKONonActiveCustomer() {
		customer = new CustomerDTO(name, email, phone, dni);
		assertTrue(this.customerAS.createCustomer(customer) > 0);
		assertTrue(this.customerAS.deleteCustomer(customer.getId()) > 0);
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
		assertEquals(this.hotelAS.createBooking(booking), HotelAS.NON_ACTIVE_CUSTOMER);
	}
	@Test public void createBookingOK() {
		customer = new CustomerDTO(name, email, phone, dni);
		assertTrue(this.customerAS.createCustomer(customer) > 0);
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
		assertTrue(this.hotelAS.createBooking(booking) > 0);
	}
	@Test public void readBookingOK() {
		customer = new CustomerDTO(name, email, phone, dni);
		assertTrue(this.customerAS.createCustomer(customer) > 0);
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
		assertTrue(this.hotelAS.createBooking(booking) > 0);
		assertNotNull(this.hotelAS.readBooking(booking.getId()));
	}
	@Test public void readBookingKO() {
		assertNull(this.hotelAS.readBooking(INF));
	}
	@Test public void readRoomOK() {
		this.room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
		assertTrue(this.roomAS.createRoom(room) > 0);
		assertNotNull(this.hotelAS.readRoom(room.getId()));
	}
	@Test public void readRoomKO() {
		assertNull(this.hotelAS.readRoom(INF));
	}
	@Test public void updateBookingKONonExistentCustomer() {
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, INF);
		assertEquals(this.hotelAS.updateBooking(booking), HotelAS.NON_EXISTENT_CUSTOMER);
	}
	@Test public void updateBookingKONonActiveCustomer() {
		customer = new CustomerDTO(name, email, phone, dni);
		assertTrue(this.customerAS.createCustomer(customer) > 0);
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
		assertTrue(this.hotelAS.createBooking(booking) > 0);
		assertTrue(this.customerAS.deleteCustomer(customer.getId()) > 0);
		assertEquals(this.hotelAS.updateBooking(booking), HotelAS.NON_ACTIVE_CUSTOMER);
	}
	@Test public void updateBookingKONonExistentBooking() {
		customer = new CustomerDTO(INF, name, email, phone, dni, active);
		assertEquals(this.hotelAS.updateBooking(booking), HotelAS.NON_EXISTENT_BOOKING);
	}
	@Test public void updateBookingOK() {
		customer = new CustomerDTO(name, email, phone, dni);
		assertTrue(this.customerAS.createCustomer(customer) > 0);
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
		assertTrue(this.hotelAS.createBooking(booking) > 0);
		assertTrue(this.hotelAS.updateBooking(booking) > 0);
	}
	@Test public void deleteBookingKONonExistentBooking() {
		assertEquals(this.hotelAS.deleteBooking(INF), HotelAS.NON_EXISTENT_BOOKING);
	}
	@Test public void deleteBookingKONonActiveBooking() {
		customer = new CustomerDTO(name, email, phone, dni);
		assertTrue(this.customerAS.createCustomer(customer) > 0);
		booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
		assertTrue(this.hotelAS.createBooking(booking) > 0);
		assertTrue(this.hotelAS.deleteBooking(booking.getId()) > 0);
		assertEquals(this.hotelAS.deleteBooking(booking.getId()), HotelAS.NON_ACTIVE_BOOKING);
	}
}
