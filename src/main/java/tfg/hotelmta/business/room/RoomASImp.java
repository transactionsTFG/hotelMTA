package tfg.hotelmta.business.room;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import tfg.hotelmta.business.exception.ExceptionAS;
import tfg.hotelmta.integration.SingletonEntityManager;
import tfg.hotelmta.integration.transaction.Transaction;
import tfg.hotelmta.integration.transaction.TransactionManager;

public class RoomASImp implements RoomAS {

    /*    @Override
    public RoomDTO readRoom(int id) {
    EntityManagerFactory emf = SingletonEntityManager.getInstance();
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();
    RoomDTO roomDTO = null;
    et.begin();
    try {
    Room room = em.find(Room.class, id, LockModeType.OPTIMISTIC);
    if (room == null) {
    et.rollback();
    throw new Exception("Room with id " + id + " does not exist");
    }
    
    roomDTO = room.toTransfer();
    et.commit();
    
    } catch (Exception e) {
    roomDTO = null;
    } finally {
    em.close();
    }
    return roomDTO;
    }*/

    @Override
    public RoomDTO readRoom(int id) {
        RoomDTO roomDTO = null;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Room room = em.find(Room.class, id, LockModeType.OPTIMISTIC);
            if (room == null) {
                throw new ExceptionAS("Room with id " + id + " does not exist");
            }
            t.commit();
            roomDTO = room.toTransfer();
        } catch (Exception e) {
            t.rollback();
        }
        return roomDTO;
    }
}
