package tfg.hotelmta.integration;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
