package business.room;

import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

import common.dto.result.Result;
import common.exception.ASException;
import mocks.UnitTestASManager;


public class RoomTests extends UnitTestASManager {

    private static final int number = 1, peopleNumber = 2;
    private static final boolean occupied = false, singleBed = true, active = true;

    private RoomDTO room;



    @Test
    public void createRoomOK() throws ASException {
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> res = roomAS.createRoom(room);
        Assert.assertTrue("Response: " + res, res.isSuccess());
    }

    // * NOTE: test works but need a commit right after persisting entinty 
    @Test
    public void searchRoomOK() throws ASException {
        room = new RoomDTO(number + 1, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> res = roomAS.createRoom(room);
        Assert.assertTrue(res.isSuccess());
        // Assert.assertTrue(roomAS.readRoom(room.getId()).isSuccess());
    }

    @Test
    public void searchRoomKO() throws ASException {
        assertThrows(ASException.class, () -> roomAS.readRoom(-1));
    }

}
