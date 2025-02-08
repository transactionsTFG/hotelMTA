package integration.database;

import integration.environment.GlassFishEnv;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EMProducer {

    @Produces
    @PersistenceContext(unitName = GlassFishEnv.NAME_PERSISTENCE)
    private EntityManager entityManager;

}