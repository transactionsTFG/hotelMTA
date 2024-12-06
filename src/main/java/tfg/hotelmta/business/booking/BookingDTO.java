package tfg.hotelmta.business.booking;

public class BookingDTO {

    private int id;
    private String date;
    private int numberOfNights;
    private boolean withBreakfast;
    private String agencyName;
    private int peopleNumber;
    private int customerId;
    private boolean active;

    public BookingDTO(int id, String date, int numberOfNights, boolean withBreakfast, String agencyName,
            int peopleNumber, int customerId, boolean active) {
        super();
        this.id = id;
        this.date = date;
        this.numberOfNights = numberOfNights;
        this.withBreakfast = withBreakfast;
        this.agencyName = agencyName;
        this.peopleNumber = peopleNumber;
        this.customerId = customerId;
        this.active = active;
    }

    public BookingDTO(String date, int numberOfNights, boolean withBreakfast, String agencyName,
            int peopleNumber, int customerId) {
        super();
        this.date = date;
        this.numberOfNights = numberOfNights;
        this.withBreakfast = withBreakfast;
        this.agencyName = agencyName;
        this.peopleNumber = peopleNumber;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public boolean isWithBreakfast() {
        return withBreakfast;
    }

    public void setWithBreakfast(boolean withBreakfast) {
        this.withBreakfast = withBreakfast;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
