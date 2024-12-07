package tfg.hotelmta.business.room;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tfg.hotelmta.business.BusinessFactory;

public class RoomTests {
    private static final int INF = 999999999;
    private RoomAS roomAS;
    
    @Before public void setUp() {
        roomAS = BusinessFactory.getInstance().createRoomAS();
    }
    
    @Test public void searchRoomOK() {
        Assert.assertNotNull(roomAS.readRoom(INF));
    }
    
    @Test public void searchRoomKO() {
        
    }
}
