package business;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import java.util.List;
import javax.persistence.ManyToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "RoomBO.findByid", query = "select obj from RoomBO obj where :id = obj.id "),
		@NamedQuery(name = "RoomBO.findBynumber", query = "select obj from RoomBO obj where :number = obj.number "),
		@NamedQuery(name = "RoomBO.findByoccupied", query = "select obj from RoomBO obj where :occupied = obj.occupied "),
		@NamedQuery(name = "RoomBO.findBysingleBed", query = "select obj from RoomBO obj where :singleBed = obj.singleBed "),
		@NamedQuery(name = "RoomBO.findByactive", query = "select obj from RoomBO obj where :active = obj.active "),
		@NamedQuery(name = "RoomBO.findBybookingBO", query = "select obj from RoomBO obj where :bookingBO MEMBER OF obj.bookingBO "),
		@NamedQuery(name = "RoomBO.findBypeopleNumber", query = "select obj from RoomBO obj where :peopleNumber = obj.peopleNumber ") })
public class RoomBO implements Serializable {
	private static final long serialVersionUID = 0;

	public RoomBO() {}

	@Id
	private int id;
	private int number;
	private boolean occupied;
	private boolean singleBed;
	private boolean active;
	@ManyToMany(mappedBy = "roomBO")
	private List<BookingBO> bookingBO;
	private int peopleNumber;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public boolean isSingleBed() {
		return singleBed;
	}
	public void setSingleBed(boolean singleBed) {
		this.singleBed = singleBed;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<BookingBO> getBookingBO() {
		return bookingBO;
	}
	public void setBookingBO(List<BookingBO> bookingBO) {
		this.bookingBO = bookingBO;
	}
	public int getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	
	
	
}