package integration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SingletonEntityManager {

    private static final String PERSISTENCE_NAME = "HotelMTA";

    private static EntityManagerFactory instance;

    public synchronized static EntityManagerFactory getInstance() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
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
