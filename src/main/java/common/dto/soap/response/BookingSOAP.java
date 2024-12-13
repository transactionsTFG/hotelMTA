package common.dto.soap.response;

import business.booking.BookingDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BookingSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookingSOAP {
    @XmlElement
    private int id;
    @XmlElement
    private String date;
    @XmlElement
    private int numberOfNights;
    @XmlElement
    private boolean withBreakfast;
    @XmlElement
    private String agencyName;
    @XmlElement
    private int peopleNumber;
    @XmlElement
    private int customerId;
    @XmlElement
    private boolean active;

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

    public static BookingSOAP toSOAP(BookingDTO bookingDTO) {
        BookingSOAP bookingSOAP = new BookingSOAP();
        bookingSOAP.setId(bookingDTO.getId());
        bookingSOAP.setAgencyName(bookingDTO.getAgencyName());
        bookingSOAP.setCustomerId(bookingDTO.getCustomerId());
        bookingSOAP.setDate(bookingDTO.getDate());
        bookingSOAP.setNumberOfNights(bookingDTO.getNumberOfNights());
        bookingSOAP.setPeopleNumber(bookingDTO.getPeopleNumber());
        bookingSOAP.setId(bookingDTO.getId());
        bookingSOAP.setWithBreakfast(bookingDTO.isWithBreakfast());
        bookingSOAP.setActive(bookingDTO.isActive());
        return bookingSOAP;
    }

}
