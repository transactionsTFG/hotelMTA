package business;

import business.booking.BookingAS;
import business.booking.BookingASImp;
import business.customer.CustomerAS;
import business.customer.CustomerASImp;
import business.room.RoomAS;
import business.room.RoomASImp;

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
