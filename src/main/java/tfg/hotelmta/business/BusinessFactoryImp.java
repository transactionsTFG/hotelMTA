package tfg.hotelmta.business;

import tfg.hotelmta.business.booking.BookingAS;
import tfg.hotelmta.business.booking.BookingASImp;
import tfg.hotelmta.business.customer.CustomerAS;
import tfg.hotelmta.business.customer.CustomerASImp;
import tfg.hotelmta.business.room.RoomAS;
import tfg.hotelmta.business.room.RoomASImp;

public class BusinessFactoryImp extends BusinessFactory {

    @Override
    public BookingAS createBookingAS() {
        return new BookingASImp();
    }

    @Override
    public RoomAS createRoomAS() {
        return new RoomASImp();
    }

    @Override
    public CustomerAS createCustomerAS() {
        return new CustomerASImp();
    }

}
