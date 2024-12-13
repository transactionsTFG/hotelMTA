package business.room;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import business.BusinessFactory;

public class RoomTests {

    private static final int number = 1, peopleNumber = 2;
    private static final boolean occupied = false, singleBed = true, active = true;

    private RoomAS roomAS;
    private RoomDTO room;

    @Before
    public void setUp() {
        roomAS = BusinessFactory.getInstance().createRoomAS();
    }

    @Test
    public void createRoomOK() {
        room = new RoomDTO(number, occupied, singleBed, active, peopleNumber);
        int res = this.roomAS.createRoom(room);
        Assert.assertTrue("Response: " + res, res > 0);

    }

    @Test
    public void searchRoomOK() {
        room = new RoomDTO(number + 1, occupied, singleBed, active, peopleNumber);
        Assert.assertTrue(this.roomAS.createRoom(room) > 0);
        Assert.assertNotNull(this.roomAS.readRoom(room.getId()));
    }

    @Test
    public void searchRoomKO() {
        Assert.assertNull(roomAS.readRoom(-1));
    }

}
