package mocks;

import business.booking.Booking;
import business.customer.Customer;
import business.room.Room;
import javax.persistence.EntityManager;

public class UnitTestEMGenerator {
    private static UnitTestEMGenerator GENERATE = null;

    private final EntityManager em;

    public static synchronized UnitTestEMGenerator init() {
        if (GENERATE == null)
            GENERATE = new UnitTestEMGenerator();

        return GENERATE;
    }

    private UnitTestEMGenerator() {
        this.em = SingletonEMF.getInstance().createEntityManager();
        this.em.getTransaction().begin();
        this.customerMock();
        this.roomMock();
        this.bookingMock();
    }

    private void customerMock() {
        Customer c1 = new Customer("Juan", "juan@gmail.com", "123456789", "12345678A", true);
        Customer c2 = new Customer("Jorge", "jorge@gmail.com", "123456789", "12345678B", true);
        Customer c3 = new Customer("Jose", "jose@gmail.com", "123456789", "12345678C", true);
        this.em.persist(c1);
        this.em.persist(c2);
        this.em.persist(c3);
    }

    private void roomMock() {
        Room r1 = new Room(1, true, false, false, 1);
        Room r2 = new Room(2, true, false, false, 1);
        Room r3 = new Room(3, true, false, false, 1);
        this.em.persist(r1);
        this.em.persist(r2);
        this.em.persist(r3);
    }

    private void bookingMock() {
        Booking b1 = new Booking("2025-01-01", 1, false, 1, true);
        Booking b2 = new Booking("2025-01-02", 1, false, 1, true);
        Booking b3 = new Booking("2025-01-03", 1, false, 1, true);
        this.em.persist(b1);
        this.em.persist(b2);
        this.em.persist(b3);
    }

}