package business.room;

import org.junit.Assert;
import org.junit.Test;

import common.dto.result.Result;
import common.exception.ASException;
import mocks.UnitTestASManager;


public class RoomTests extends UnitTestASManager {

    private static final int number = 1, peopleNumber = 2;
    private static final boolean occupied = false, singleBed = true, active = true;

    private RoomAS roomAS;
    private RoomDTO room;



    @Test
    public void createRoomOK() throws ASException {
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> res = this.roomAS.createRoom(room);
        Assert.assertTrue("Response: " + res, res.isSuccess());
    }

    @Test
    public void searchRoomOK() throws ASException {
        room = new RoomDTO(number + 1, occupied, singleBed, active, peopleNumber);
        Assert.assertTrue(this.roomAS.createRoom(room).isSuccess());
        Assert.assertTrue(this.roomAS.readRoom(room.getId()).isSuccess());
    }

    @Test
    public void searchRoomKO() throws ASException {
        Assert.assertFalse(roomAS.readRoom(-1).isSuccess());
    }

}
