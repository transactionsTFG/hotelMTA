package business;

public class BusinessFactoryImp extends BusinessFactory {

	@Override
	public HotelAS createHotelAS() {
		return new HotelASImp();
	}

}
