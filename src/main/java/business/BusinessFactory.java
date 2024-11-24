package business;

import business.hotel.HotelAS;

public abstract class BusinessFactory {
	
	private static BusinessFactory instance;
	
	public synchronized static BusinessFactory getInstance() {
		if (instance == null) instance = new BusinessFactoryImp();
		return instance;
	}
	
	public abstract HotelAS createHotelAS();

}
