package business.booking;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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

    private static int customerId = 0, roomId = 0, number = 99999999, bookingId = 0;

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

    private void updateBookingId() {
        bookingId++;
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
        room = new RoomDTO(roomId, number, occupied, singleBed, active,
                peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customerId);
        Result<BookingTOA> res = bookingAS.createBooking(booking, rooms);
        Assert.assertTrue(res.isSuccess());
        updateBookingId();
    }

    @Test
    public void a3CreateBookingKONonExistentCustomer() throws ASException {
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, -1);
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking,
                rooms));
    }

    @Test
    public void a4CreateBookingKONonActiveCustomer() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "B", false);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customer.getId());
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking,
                rooms));
    }

    @Test
    public void a5CreateBookingKONonExistentRoom() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "C", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        rooms = new ArrayList<>();
        number = getRandomNumber();
        room = new RoomDTO(-1, number, occupied, singleBed, active, peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customer.getId());
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking,
                rooms));
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
        // Assert.assertTrue(customerAS.deleteCustomer(customerId).isSuccess());
    }

    @Test
    public void a8CreateBookingKONonActiveRoom() throws ASException {
        rooms = new ArrayList<>();
        number = getRandomNumber();
        room = new RoomDTO(roomId, number, occupied, singleBed, false, peopleNumber);
        rooms.add(room);
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customerId);
        assertThrows(ASException.class, () -> bookingAS.createBooking(booking,
                rooms));
    }

    // // Update is like create but with an extra possible response:
    // // non_existent_booking
    // @Test
    // public void b1UpdateBookingOK() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "E", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // Assert.assertTrue(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void b2UpdateBookingKONonExistentBooking() throws ASException {
    // booking = new BookingDTO(-1, date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, number, active);
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void b3UpdateBookingKONonExistentCustomer() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "F", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // booking.setCustomerId(-1);
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void b4UpdateBookingKONonActiveCustomer() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "G", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // Assert.assertTrue(customerAS.deleteCustomer(customer.getId()).isSuccess());
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void b5UpdateBookingKONonExistentRoom() throws ASException {
    // customer = new CustomerDTO(name, email, phone, dni + "H", active);
    // Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
    // booking = new BookingDTO(date, numberOfNights, withBreakfast, agencyName,
    // peopleNumber, customer.getId());
    // Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
    // rooms.add(new RoomDTO(-1, number, false, false, false, 1));
    // Assert.assertFalse(bookingAS.updateBooking(booking, rooms).isSuccess());
    // }

    // @Test
    // public void b6UpdateBookingKONonActiveRoom() throws ASException {
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

    @Test
    public void c1Init1() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "J", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        updateCustomerId();

        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        assertTrue(roomAS.createRoom(room).isSuccess());
        updateRoomId();
    }

    @Test
    public void c1Init2() throws ASException {
        rooms = new ArrayList<>();
        rooms.add(new RoomDTO(roomId, number, occupied, singleBed, active, peopleNumber));
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customerId);
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        updateBookingId();
    }

    @Test
    public void c2DeleteBookingOK() throws ASException {
        Assert.assertTrue(bookingAS.deleteBooking(bookingId).isSuccess());
    }

    @Test
    public void c3Init1() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "K", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        updateCustomerId();

        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        assertTrue(roomAS.createRoom(room).isSuccess());
        updateRoomId();
    }

    @Test
    public void c3Init2() throws ASException {
        rooms = new ArrayList<>();
        rooms.add(new RoomDTO(roomId, number, occupied, singleBed, active, peopleNumber));
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customerId);
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        updateBookingId();
    }

    @Test
    public void c3Init3() throws ASException {
        Assert.assertTrue(bookingAS.deleteBooking(bookingId).isSuccess());
    }

    @Test
    public void c4DeleteBookingKONonExistentBooking() throws ASException {
        assertThrows(ASException.class, () -> bookingAS.deleteBooking(-1));
    }

    @Test
    public void c5DeleteBookingKONonActiveBooking() throws ASException {
        assertThrows(ASException.class, () -> bookingAS.deleteBooking(bookingId));
    }

    @Test
    public void c6Init1() throws ASException {
        customer = new CustomerDTO(name, email, phone, dni + "L", true);
        Assert.assertTrue(customerAS.createCustomer(customer).isSuccess());
        updateCustomerId();

        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        assertTrue(roomAS.createRoom(room).isSuccess());
        updateRoomId();
    }

    @Test
    public void c6Init2() throws ASException {
        rooms = new ArrayList<>();
        rooms.add(new RoomDTO(roomId, number, occupied, singleBed, active, peopleNumber));
        booking = new BookingDTO(date, numberOfNights, withBreakfast,
                peopleNumber, customerId);
        Assert.assertTrue(bookingAS.createBooking(booking, rooms).isSuccess());
        updateBookingId();
    }

    // NOTE: Non existent/active customer doesn't need to be tested
    @Test
    public void c7ReadBookingOK() throws ASException {
        Assert.assertTrue(bookingAS.readBooking(bookingId).isSuccess());
    }

    @Test
    public void c8ReadBookingKO() throws ASException {
        assertThrows(ASException.class, () -> bookingAS.readBooking(-1));
    }

}
