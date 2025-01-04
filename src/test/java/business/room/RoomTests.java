package business.room;

import static org.junit.Assert.assertThrows;

import java.util.Random;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import common.dto.result.Result;
import common.exception.ASException;
import mocks.UnitTestASManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoomTests extends UnitTestASManager {

    private static final int peopleNumber = 2;
    private static final boolean occupied = false, singleBed = true, active = true;
    private static int number = 99999999, roomId = 0;

    private RoomDTO room;

    private int getRandomNumber() {
        return new Random().nextInt(number);
    }

    private void updateRoomId() {
        roomId++;
    }

    @Test
    public void a1CreateRoomOK() throws ASException {
        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> res = roomAS.createRoom(room);
        Assert.assertTrue("Response: " + res, res.isSuccess());
        updateRoomId();
    }

    // * NOTE: test works but need a commit right after persisting entinty
    @Test
    public void b1Init() throws ASException {
        number = getRandomNumber();
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        Result<RoomDTO> res = roomAS.createRoom(room);
        Assert.assertTrue(res.isSuccess());

    }

    @Test
    public void b2SearchRoomOK() throws ASException {
        Assert.assertTrue(roomAS.readRoom(roomId).isSuccess());
    }

    @Test
    public void b3SearchRoomKO() throws ASException {
        assertThrows(ASException.class, () -> roomAS.readRoom(-1));
    }

}
