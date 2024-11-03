package integration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SingletonEntityManager {
	private static EntityManagerFactory instance;

	public synchronized static EntityManagerFactory getInstance() {
		if (instance == null) {
			instance = Persistence.createEntityManagerFactory("hotelMTA");
		}

		return instance;
	}
	
	@Override
	public void finalize() {
		if (instance != null) {
			instance.close();
		}
	}
}