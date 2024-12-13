package common.dto.soap.request;

@XmlRootElement(name = "MakeReservationRequestSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
public class MakeReservationRequestSOAP {
    @XmlElement
    private CustomerDTO customer;

    @XmlElement
    private ReservationDTO reservation;

    @XmlElement
    private long idFlight;

    @XmlElement
    private int numberOfSeats;

    public MakeReservationRequestSOAP() {
    }

    public MakeReservationRequestSOAP(CustomerDTO customer, ReservationDTO reservation, long idFlight,
            int numberOfSeats) {
        this.customer = customer;
        this.reservation = reservation;
        this.idFlight = idFlight;
        this.numberOfSeats = numberOfSeats;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    public long getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(long idFlight) {
        this.idFlight = idFlight;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}