package business;

import business.booking.BookingAS;
import business.customer.CustomerAS;
import business.room.RoomAS;

public abstract class BusinessFactory {

    private static BusinessFactory instance;

    public synchronized static BusinessFactory getInstance() {
        if (instance == null) {
            instance = new BusinessFactoryImp();
        }
        return instance;
    }

    public abstract BookingAS createBookingAS();

    public abstract RoomAS createRoomAS();

    public abstract CustomerAS createCustomerAS();

}
