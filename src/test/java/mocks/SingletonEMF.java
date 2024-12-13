package mocks;

import integration.environment.PersistenceConfig;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SingletonEMF {
    private static EntityManagerFactory emf = null;

    private SingletonEMF() {
        /* SINGLETON PATTERN */}

    public static synchronized EntityManagerFactory getInstance() {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory(PersistenceConfig.getInstance().getPersistenceName());
        return emf;
    }

}