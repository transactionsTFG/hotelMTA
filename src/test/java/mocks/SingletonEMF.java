package mocks;

import integration.environment.PersistenceConfig;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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