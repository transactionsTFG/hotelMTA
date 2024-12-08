package tfg.hotelmta.business.room;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.util.List;
import tfg.hotelmta.business.exception.ExceptionAS;
import tfg.hotelmta.integration.transaction.Transaction;
import tfg.hotelmta.integration.transaction.TransactionManager;

public class RoomASImp implements RoomAS {

    @Override
    public int createRoom(RoomDTO roomDTO) {
        int res = -1;
        if (!this.isValid(roomDTO)) {
            return -2;
        }

        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            TypedQuery<Room> query = em.createNamedQuery("tfg.hotelmta.business.room.getByRoomNumber", Room.class);
            query.setParameter("number", roomDTO.getNumber());
            List<Room> resultList = query.getResultList();
            Room room = null;
            if (resultList.isEmpty()) {
                room = new Room(roomDTO);
                em.persist(room);
                t.commit();
                res = room.getId();
                roomDTO.setId(res);
            } else {
                room = resultList.getFirst();
                if (room.isActive()) {
                    res = -3;
                    throw new ExceptionAS("Room is already active");
                }
                room.setActive(true);
                room.setNumber(roomDTO.getNumber());
                room.setOccupied(roomDTO.isOccupied());
                room.setPeopleNumber(roomDTO.getPeopleNumber());
                room.setSingleBed(roomDTO.isSingleBed());
                t.commit();
                res = room.getId();
                roomDTO.setId(res);
            }
        } catch (Exception e) {
            t.rollback();
        }
        return res;
    }

    @Override
    public RoomDTO readRoom(int id) {
        RoomDTO roomDTO = null;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
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

    private boolean isValid(RoomDTO room) {
        return true;
    }
}
