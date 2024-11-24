package business;

import business.hotel.HotelAS;
import business.hotel.HotelASImp;

public class BusinessFactoryImp extends BusinessFactory {

	@Override
	public HotelAS createHotelAS() {
		return new HotelASImp();
	}

}
