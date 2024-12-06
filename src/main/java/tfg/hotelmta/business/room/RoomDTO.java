package tfg.hotelmta.business.room;

public class RoomDTO {

    private int id;
    private int number;
    private boolean occupied;
    private boolean singleBed;
    private boolean active;
    private int peopleNumber;

    public RoomDTO(int id, int number, boolean occupied, boolean singleBed, boolean active, int peopleNumber) {
        super();
        this.id = id;
        this.number = number;
        this.occupied = occupied;
        this.singleBed = singleBed;
        this.active = active;
        this.peopleNumber = peopleNumber;
    }

    public RoomDTO(int number, boolean occupied, boolean singleBed, boolean active, int peopleNumber) {
        super();
        this.number = number;
        this.occupied = occupied;
        this.singleBed = singleBed;
        this.active = active;
        this.peopleNumber = peopleNumber;
    }

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

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

}
