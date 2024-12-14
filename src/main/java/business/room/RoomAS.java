package business.room;

import common.dto.result.Result;
import common.exception.ASException;

public interface RoomAS {

    public Result<RoomDTO> createRoom(RoomDTO roomDTO) throws ASException;

    public Result<RoomDTO> readRoom(int id) throws ASException;

    public Result<Void> deleteRoom(int id) throws ASException;
}
