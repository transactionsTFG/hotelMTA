package business.room;

import common.dto.result.Result;

public interface RoomAS {

    public Result<RoomDTO> createRoom(RoomDTO roomDTO);

    public Result<RoomDTO> readRoom(int id);

    public Result<RoomDTO> readRoomByNumber(int number);

    public Result<Void> deleteRoom(int id);
}
