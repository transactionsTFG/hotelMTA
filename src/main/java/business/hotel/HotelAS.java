package business.hotel;

import business.hotel.booking.BookingDTO;
import business.hotel.room.RoomDTO;

public interface HotelAS {
	
	public static final int SINTACTICAL_ERROR = -1;
	public static final int UNEXPECTED_ERROR = -2;
	public static final int NON_EXISTENT_BOOKING = -3;
	public static final int NON_EXISTENT_CUSTOMER = -4;
	public static final int NON_ACTIVE_CUSTOMER = -5;
	public static final int NON_ACTIVE_BOOKING = -6;
	
	public int createBooking(BookingDTO booking);
	public BookingDTO readBooking(int id);
	public RoomDTO readRoom(int id);
	public int updateBooking(BookingDTO booking);
	public int deleteBooking(int id);
	
	
}
