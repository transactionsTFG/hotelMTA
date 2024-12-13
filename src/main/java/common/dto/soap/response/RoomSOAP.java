package common.dto.soap.response;

@XmlRootElement(name = "RoomSOAP")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomSOAP {
    @XmlElement
    private long id;
    @XmlElement
	private String date;
    @XmlElement
    private double total;
    @XmlElement
	private long idCustomer;
    @XmlElement
    private boolean active;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
    
    public static RoomSOAP toSOAP(ReservationDTO reservationDTO) {
        RoomSOAP RoomSOAP = new RoomSOAP();
        RoomSOAP.setId(reservationDTO.getId());
        RoomSOAP.setDate(reservationDTO.getDate());
        RoomSOAP.setTotal(reservationDTO.getTotal());
        RoomSOAP.setIdCustomer(reservationDTO.getIdCustomer());
        RoomSOAP.setActive(reservationDTO.isActive());
        return RoomSOAP;
    }
}
