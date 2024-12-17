package business.booking;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import business.customer.CustomerAS;
import business.customer.CustomerDTO;
import business.room.RoomAS;
import business.room.RoomDTO;
import common.dto.result.Result;
import common.exception.ASException;
import mocks.UnitTestASManager;

public class BookingTests extends UnitTestASManager {

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

    @Test
    public void createBookingOK() throws ASException {
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> roomRes = roomAS.createRoom(room);
        Assert.assertTrue(roomRes.isSuccess());
        rooms.add(room);
        customer = new CustomerDTO(name, email, phone, dni, true);
        Result<CustomerDTO> customerRes = customerAS.createCustomer(customer);
        Assert.assertTrue(customerRes.isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Result<BookingTOA> res = bookingAS.createBooking(booking, rooms);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void createBookingKONonExistentCustomer() throws ASException {
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, -1);
        Assert.assertFalse(bookingAS.createBooking(booking, rooms).isSuccess());
    }

    @Test
    public void createBookingKONonActiveCustomer() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, false);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertFalse(bookingAS.createBooking(booking, rooms).isSuccess());
    }

    @Test
    public void createBookingKONonExistentRoom() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        room = new RoomDTO(-1, number, occupied, singleBed, active, peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertFalse(bookingAS.createBooking(booking, rooms).isSuccess());
    }

    @Test
    public void createBookingKONonActiveRoom() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Assert.assertTrue(roomAS.createRoom(room).isSuccess());
        Assert.assertTrue(roomAS.deleteRoom(room.getId()).isSuccess());
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertFalse(bookingAS.createBooking(booking, rooms).isSuccess());
    }

    // Update is like create but with an extra possible response:
    // non_existent_booking
    @Test
    public void updateBookingOK() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, active);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        Assert.assertTrue(bookingAS.updateBooking(booking, rooms).isSuccess());
    }

    @Test
    public void updateBookingKONonExistentBooking() throws ASException {
        booking = new BookingDTO(-1, date, numberOfNights, withBreakfast, agencyName, peopleNumber, number, active);
        Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    }

    @Test
    public void updateBookingKONonExistentCustomer() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, active);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        booking.setCustomerId(-1);
        Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    }

    @Test
    public void updateBookingKONonActiveCustomer() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, active);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        Assert.assertTrue(customerAS.deleteCustomer(customer.getId()).isSuccess());
        Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    }

    @Test
    public void updateBookingKONonExistentRoom() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, active);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        rooms.add(new RoomDTO(-1, number, false, false, false, 1));
        Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    }

    @Test
    public void updateBookingKONonActiveRoom() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, active);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Assert.assertTrue(roomAS.createRoom(room).isSuccess());
        Assert.assertTrue(roomAS.deleteRoom(room.getId()).isSuccess());
        rooms.add(room);
        Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    }

    @Test
    public void deleteBookingOK() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        Assert.assertTrue(bookingAS.deleteBooking(booking.getId()).isSuccess());
    }

    @Test
    public void deleteBookingKONonExistentBooking() throws ASException {
        Assert.assertFalse(bookingAS.deleteBooking(-1).isSuccess());
    }

    @Test
    public void deleteBookingKONonActiveBooking() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni, true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        Assert.assertTrue(bookingAS.deleteBooking(booking.getId()).isSuccess());
        Assert.assertFalse( bookingAS.deleteBooking(booking.getId()).isSuccess());
    }

    // NOTE: Non existent/active customer doesn't need to be tested
    @Test
    public void readBookingOK() throws ASException {
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> roomRes = roomAS.createRoom(room);
        Assert.assertTrue("Error response: " + roomRes, roomRes.isSuccess());
        rooms.add(room);
        customer = new CustomerDTO(name, email, phone, dni, true);
        Result<CustomerDTO> customerRes = customerAS.createCustomer(customer);
        Assert.assertTrue("Error response: " + customerRes, customerRes.isSuccess());
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        Result<BookingTOA> res = bookingAS.createBooking(booking, rooms);
        Assert.assertTrue("Error response: " + res, res.isSuccess());
        Assert.assertNotNull(bookingAS.readBooking(booking.getId()));
    }

    @Test
    public void readBookingKO() throws ASException {
        Assert.assertNull(bookingAS.readBooking(-1));
    }

}
