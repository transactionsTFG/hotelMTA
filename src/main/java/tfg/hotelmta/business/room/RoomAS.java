package tfg.hotelmta.business.room;

public interface RoomAS {
    public int createRoom(RoomDTO roomDTO);
    public RoomDTO readRoom(int id);
}
