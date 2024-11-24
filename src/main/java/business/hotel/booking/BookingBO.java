package business.hotel.booking;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import business.hotel.customer.CustomerBO;
import business.hotel.room.RoomBO;

import javax.persistence.NamedQueries;
import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.ManyToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "BookingBO.findByid", query = "select obj from BookingBO obj where :id = obj.id "),
		@NamedQuery(name = "BookingBO.findBydate", query = "select obj from BookingBO obj where :date = obj.date "),
		@NamedQuery(name = "BookingBO.findBynumberOfNights", query = "select obj from BookingBO obj where :numberOfNights = obj.numberOfNights "),
		@NamedQuery(name = "BookingBO.findBywithBreakfast", query = "select obj from BookingBO obj where :withBreakfast = obj.withBreakfast "),
		@NamedQuery(name = "BookingBO.findByagencyName", query = "select obj from BookingBO obj where :agencyName = obj.agencyName "),
		@NamedQuery(name = "BookingBO.findBycustomerBO", query = "select obj from BookingBO obj where :customerBO = obj.customerBO "),
		@NamedQuery(name = "BookingBO.findByroomBO", query = "select obj from BookingBO obj where :roomBO MEMBER OF obj.roomBO "),
		@NamedQuery(name = "BookingBO.findBypeopleNumber", query = "select obj from BookingBO obj where :peopleNumber = obj.peopleNumber ") })
public class BookingBO implements Serializable {
	private static final long serialVersionUID = 0;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Version
	private int version;
	private String date;
	private int numberOfNights;
	private boolean withBreakfast;
	private String agencyName;
	private boolean active;
	@ManyToOne
	private CustomerBO customerBO;
	@ManyToMany
	private List<RoomBO> roomBO;
	private int peopleNumber;

	public BookingBO() {}
	public BookingBO(BookingDTO bookingDTO) {
		this.setId(bookingDTO.getId());
		this.setDate(bookingDTO.getDate());
		this.setNumberOfNights(bookingDTO.getNumberOfNights());
		this.setWithBreakfast(bookingDTO.isWithBreakfast());
		this.setAgencyName(bookingDTO.getAgencyName());
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public CustomerBO getCustomerBO() {
		return customerBO;
	}
	public void setCustomerBO(CustomerBO customerBO) {
		this.customerBO = customerBO;
	}
	public List<RoomBO> getRoomBO() {
		return roomBO;
	}
	public void setRoomBO(List<RoomBO> roomBO) {
		this.roomBO = roomBO;
	}
	public int getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	
	public BookingDTO toTransfer() {
		return new BookingDTO(id, date, numberOfNights, withBreakfast, agencyName, peopleNumber, customerBO.getId(), active);
	}

}