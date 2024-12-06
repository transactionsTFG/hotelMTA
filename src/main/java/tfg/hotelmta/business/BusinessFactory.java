package tfg.hotelmta.business;

import tfg.hotelmta.business.booking.BookingAS;
import tfg.hotelmta.business.room.RoomAS;

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

}
