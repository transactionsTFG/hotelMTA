package tfg.hotelmta.business.booking;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tfg.hotelmta.business.BusinessFactory;
import tfg.hotelmta.business.customer.CustomerAS;
import tfg.hotelmta.business.customer.CustomerDTO;
import tfg.hotelmta.business.room.RoomAS;
import tfg.hotelmta.business.room.RoomDTO;
import tfg.hotelmta.business.utils.ErrorResponses;

public class BookingTests {

    // Booking attributes
    private static final String date = "02-02-2025", agencyName = "UCM";
    private static final int numberOfNights = 4;
    private static final boolean withBreakfast = true;
    // Room attributes
    private static final int number = 1, peopleNumber = 2;
    private static final boolean occupied = false, singleBed = true, active = true;
    // Customer attributes
    private static final String name = "Juan", email = "juan@gmail.com", phone = "123456789", dni = "12345678A";

    private BookingAS bookingAS;
    private RoomAS roomAS;
    private CustomerAS customerAS;
    private BookingDTO booking;
    private RoomDTO room;
    private CustomerDTO customer;
    private List<RoomDTO> rooms;

    @Before
    public void setUp() {
        bookingAS = BusinessFactory.getInstance().createBookingAS();
        roomAS = BusinessFactory.getInstance().createRoomAS();
        customerAS = BusinessFactory.getInstance().createCustomerAS();
        rooms = new ArrayList<>();
    }

    @Test
    public void createBookingOK() {
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        int roomRes = roomAS.createRoom(room);
        Assert.assertTrue("Error response: " + roomRes, roomRes > 0 && room.getId() > 0);
        rooms.add(room);
        customer = new CustomerDTO(name, email, phone, dni, true);
        int customerRes = customerAS.createCustomer(customer);
        Assert.assertTrue("Error response: " + customerRes, customerRes > 0 && customer.getId() > 0);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        int res = bookingAS.createBooking(booking, rooms);
        Assert.assertTrue("Error response: " + res, res > 0);
    }

    @Test
    public void createBookingKONonExistentCustomer() {
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, -1);
        Assert.assertEquals(ErrorResponses.NON_EXISTENT_CUSTOMER, bookingAS.createBooking(booking, rooms));
    }

    @Test
    public void createBookingKONonActiveCustomer() {
        customer = new CustomerDTO(name, email, phone, dni, false);
        Assert.assertTrue(customerAS.createCustomer(customer) > 0);
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertEquals(ErrorResponses.NON_ACTIVE_CUSTOMER, bookingAS.createBooking(booking, rooms));
    }

    // Update is like create but with an extra possible response
    @Test
    public void deleteBookingOK() {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer) > 0);
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms) > 0);
        Assert.assertTrue(bookingAS.deleteBooking(booking.getId()) > 0);
    }

    @Test public void deleteBookingKONonExistentBooking() {
        Assert.assertEquals(ErrorResponses.NON_EXISTENT_BOOKING, bookingAS.deleteBooking(-1));
    }
    
    @Test
    public void deleteBookingKONonActiveBooking() {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer) > 0);
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms) > 0);
        Assert.assertTrue(bookingAS.deleteBooking(booking.getId()) > 0);
        Assert.assertEquals(ErrorResponses.NON_ACTIVE_BOOKING, bookingAS.deleteBooking(booking.getId()));
    }
    
    @Test public void readBookingOK() {
                room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        int roomRes = roomAS.createRoom(room);
        Assert.assertTrue("Error response: " + roomRes, roomRes > 0 && room.getId() > 0);
        rooms.add(room);
        customer = new CustomerDTO(name, email, phone, dni, true);
        int customerRes = customerAS.createCustomer(customer);
        Assert.assertTrue("Error response: " + customerRes, customerRes > 0 && customer.getId() > 0);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        int res = bookingAS.createBooking(booking, rooms);
        Assert.assertTrue("Error response: " + res, res > 0);
        Assert.assertNotNull(bookingAS.readBooking(booking.getId()));
    }
    
    @Test public void readBookingKO() {
        Assert.assertNull(bookingAS.readBooking(-1));
    }

}
