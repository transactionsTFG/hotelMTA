package tfg.hotelmta.business.booking;

public interface BookingAS {

    public int createBooking(BookingDTO bookingDTO);

    public int updateBooking(BookingDTO bookingDTO);

    public int deleteBooking(int id);

    public BookingDTO readBooking(int id);
}
