package business.booking;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import business.customer.CustomerDTO;
import business.room.RoomDTO;
import common.dto.result.Result;
import common.exception.ASException;
import mocks.UnitTestASManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookingTests extends UnitTestASManager {

    // Booking attributes
    private static final String date = "02-02-2025", agencyName = "UCM";
    private static final int numberOfNights = 4;
    private static final boolean withBreakfast = true;
    // Room attributes
    private static final int peopleNumber = 2;
    private static final boolean occupied = false, singleBed = true, active = true;
    // Customer attributes
    private static final String name = "Juan", email = "juan@gmail.com", phone = "123456789", dni = "12345678";

    private int customerId = 1, roomId = 1, number = 99999999;

    private BookingDTO booking;
    private RoomDTO room;
    private CustomerDTO customer;
    private List<RoomDTO> rooms;

    private void updateCustomerId() {
        customerId++;
    }

    private void updateRoomId() {
        roomId++;
    }

    private int getRandomNumber() {
        return new Random().nextInt(number);
    }

    @Test
    public void a1Init() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "A", true);
        Result<CustomerDTO> customerRes = customerAS.createCustomer(customer);
        customer.setId(customerId);
        Assert.assertTrue(customerRes.isSuccess());
        updateCustomerId();
        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> roomRes = roomAS.createRoom(room);
        Assert.assertTrue(roomRes.isSuccess());
        updateRoomId();
    }

    @Test
    public void a2CreateBookingOK() throws ASException {
        rooms = new ArrayList<>();
        number = getRandomNumber();
        room = new RoomDTO(roomId, number, occupied, singleBed, active, peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customerId);
        Result<BookingTOA> res = bookingAS.createBooking(booking, rooms);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void a3CreateBookingKONonExistentCustomer() throws ASException {
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, -1);
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking, rooms));
    }

    @Test
    public void a4CreateBookingKONonActiveCustomer() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "B", false);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking, rooms));
    }

    @Test
    public void a5CreateBookingKONonExistentRoom() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "C", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        number = getRandomNumber();
        room = new RoomDTO(-1, number, occupied, singleBed, active, peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customer.getId());
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking, rooms));
    }

    @Test
    public void a6Init() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "D", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        updateCustomerId();
        rooms = new ArrayList<>();
        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Assert.assertTrue(roomAS.createRoom(room).isSuccess());
        updateRoomId();
    }

    @Test
    public void a7Init() throws ASException {
        Assert.assertTrue(roomAS.deleteRoom(roomId).isSuccess());
        Assert.assertTrue(customerAS.deleteCustomer(customerId).isSuccess());
    }

    @Test
    public void a8CreateBookingKONonActiveRoom() throws ASException {
        rooms = new ArrayList<>();
        number = getRandomNumber();
        room = new RoomDTO(roomId, number, occupied, singleBed, false, peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName, peopleNumber, customerId);
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking, rooms));
    }

    // // Update is like create but with an extra possible response:
    // // non_existent_booking
    // @Test
    // public void updateBookingOK() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "E", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // Assert.assertTrue(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void updateBookingKONonExistentBooking() throws ASException {
    // booking = new BookingDTO(-1, date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, number, active);
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void updateBookingKONonExistentCustomer() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "F", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // booking.setCustomerId(-1);
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void updateBookingKONonActiveCustomer() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "G", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // Assert.assertTrue(customerAS.deleteCustomer(customer.getId()).isSuccess());
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void updateBookingKONonExistentRoom() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "H", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // rooms.add(new RoomDTO(-1, number, false, false, false, 1));
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void updateBookingKONonActiveRoom() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "I", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
    // Assert.assertTrue(roomAS.createRoom(room).isSuccess());
    // Assert.assertTrue(roomAS.deleteRoom(room.getId()).isSuccess());
    // rooms.add(room);
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void deleteBookingOK() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "J", true);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // rooms = new ArrayList<>();
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // Assert.assertTrue(bookingAS.deleteBooking(booking.getId()).isSuccess());
    // }

    // @Test
    // public void deleteBookingKONonExistentBooking() throws ASException {
    // Assert.assertFalse(bookingAS.deleteBooking(-1).isSuccess());
    // }

    // @Test
    // public void deleteBookingKONonActiveBooking() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "K", true);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // rooms = new ArrayList<>();
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // Assert.assertTrue(bookingAS.deleteBooking(booking.getId()).isSuccess());
    // Assert.assertFalse( bookingAS.deleteBooking(booking.getId()).isSuccess());
    // }

    // // NOTE: Non existent/active customer doesn't need to be tested
    // @Test
    // public void readBookingOK() throws ASException {
    // room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
    // Result<RoomDTO> roomRes = roomAS.createRoom(room);
    // Assert.assertTrue("Error response: " + roomRes, roomRes.isSuccess());
    // rooms = new ArrayList<>();
    // rooms.add(room);
    // customer = new CustomerDTO(name, email, phone, dni + "L", true);
    // Result<CustomerDTO> customerRes = customerAS.createCustomer(customer);
    // Assert.assertTrue("Error response: " + customerRes, customerRes.isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Result<BookingTOA> res = bookingAS.createBooking(booking, rooms);
    // Assert.assertTrue("Error response: " + res, res.isSuccess());
    // Assert.assertNotNull(bookingAS.readBooking(booking.getId()));
    // }

    @Test
    public void readBookingKO() throws ASException {
        assertThrows(ASException.class, () -> bookingAS.readBooking(-1));
    }

}
