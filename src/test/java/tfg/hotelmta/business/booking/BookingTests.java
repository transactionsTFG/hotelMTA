package tfg.hotelmta.business.booking;

import org.junit.Before;
import org.junit.Test;
import tfg.hotelmta.business.BusinessFactory;

public class BookingTests {

    private BookingAS bookingAS;

    @Before
    public void setUp() {
        bookingAS = BusinessFactory.getInstance().createBookingAS();
    }

}
