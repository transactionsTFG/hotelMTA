// package mocks;

// import business.booking.Booking;
// import business.booking.BookingDTO;
// import business.customer.Customer;
// import business.customer.CustomerDTO;
// import javax.persistence.EntityManager;
// import javax.persistence.TypedQuery;

// public class ConsultMocks {
//     private final EntityManager em;

//     public ConsultMocks(EntityManager em) {
//         this.em = em;
//     }

//     public CustomerDTO getCustomerMockFirst() {
//         TypedQuery<Customer> query = em.createNamedQuery("business.customer.Customer.getAll", Customer.class);
//         query.setMaxResults(1);
//         Customer r = query.getResultStream().findFirst().orElse(null);
//         return r.toDTO();
//     }

//     public BookingDTO getReservationMockFirst() {
//         TypedQuery<Booking> query = em.createNamedQuery("business.reservation.Reservation.getAll",
//                 Booking.class);
//         query.setMaxResults(1);
//         Booking r = query.getResultStream().findFirst().orElse(null);
//         return r.toDTO();
//     }

//     public BookingDTO getReservationMockById(final long id) {
//         Booking reservation = em.find(Booking.class, id);
//         return reservation.toDTO();
//     }

// }