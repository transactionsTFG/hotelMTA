package mocks;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import business.booking.BookingAS;
import business.booking.BookingASImp;
import business.customer.CustomerAS;
import business.customer.CustomerASImp;
import business.room.RoomAS;
import business.room.RoomASImp;
import integration.environment.PersistenceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class UnitTestASManager {

    protected static BookingAS bookingAS;
    protected static RoomAS roomAS;
    protected static CustomerAS customerAS;
    protected static ConsultMocks getMock;
    protected EntityManager em;
    protected static EntityManagerFactory emf;

    @BeforeClass
    public static void setUp() {
        UnitTestEMGenerator.init();
        emf = Persistence.createEntityManagerFactory(PersistenceConfig.getInstance().getPersistenceName());
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        if (emf.isOpen())
            emf.close();
    }

    @Before
    public void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        getMock = new ConsultMocks(em);
        bookingAS = new BookingASImp(em);
        roomAS = new RoomASImp(em);
        customerAS = new CustomerASImp(em);
    }

    @After
    public void rollbackTransaction() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();

        if (em.isOpen())
            em.close();
    }

    public void commit() {
        if (em.getTransaction().isActive())
            this.em.getTransaction().commit();
    }

}