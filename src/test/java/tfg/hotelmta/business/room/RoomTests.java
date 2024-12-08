package tfg.hotelmta.business.room;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tfg.hotelmta.business.BusinessFactory;

public class RoomTests {
    private RoomAS roomAS;
    
    @Before public void setUp() {
        roomAS = BusinessFactory.getInstance().createRoomAS();
    }
    
    @Test public void searchRoomOK() {
        Assert.assertTrue(false);
    }
    
    @Test public void searchRoomKO() {
        Assert.assertNull(roomAS.readRoom(-1));
    }
    
}
