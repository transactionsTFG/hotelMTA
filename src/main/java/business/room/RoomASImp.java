package business.room;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import business.exception.ASException;
import business.utils.ErrorResponses;
import integration.transaction.Transaction;
import integration.transaction.TransactionManager;

public class RoomASImp implements RoomAS {

    private final EntityManager em;

    @Inject
    public RoomASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public int createRoom(RoomDTO roomDTO) {
        int res = ErrorResponses.SYNTAX_ERROR;
        if (!this.isValid(roomDTO)) {
            return res;
        }

        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            TypedQuery<Room> query = em.createNamedQuery("business.room.getByRoomNumber", Room.class);
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
                    res = ErrorResponses.ACTIVE_ROOM;
                    throw new ASException("Room is already active");
                } else {
                    room.setActive(true);
                    room.setNumber(roomDTO.getNumber());
                    room.setOccupied(roomDTO.isOccupied());
                    room.setPeopleNumber(roomDTO.getPeopleNumber());
                    room.setSingleBed(roomDTO.isSingleBed());
                    t.commit();
                    res = ErrorResponses.NON_ACTIVE_ROOM;
                    roomDTO.setId(room.getId());
                }
            }
        } catch (Exception e) {
            t.rollback();
        }
        return res;
    }

    @Override
    @Transactional
    public RoomDTO readRoom(int id) {
        RoomDTO roomDTO = null;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Room room = em.find(Room.class, id, LockModeType.OPTIMISTIC);
            if (room == null) {
                throw new ASException("Room with id " + id + " does not exist");
            }
            t.commit();
            roomDTO = room.toDTO();
        } catch (Exception e) {
            t.rollback();
        }
        return roomDTO;
    }

    @Override
    @Transactional
    public int deleteRoom(int id) {
        int res = ErrorResponses.NON_EXISTENT_ROOM;
        Transaction t = TransactionManager.getInstance().newTransaccion();
        t.start();
        EntityManager em = (EntityManager) t.getResource();
        try {
            Room room = em.find(Room.class, id);
            if (room == null) {
                throw new ASException("Non existent room");
            }

            if (!room.isActive()) {
                res = ErrorResponses.NON_ACTIVE_ROOM;
                throw new ASException("Non active room");
            }

            room.setActive(false);
            t.commit();
            res = id;

        } catch (Exception e) {
            if (!(e instanceof ASException)) {
                res = ErrorResponses.UNEXPECTED_ERROR;
            }
            t.rollback();
        }
        return res;
    }

    private boolean isValid(RoomDTO room) {
        return room.getNumber() > 0 && room.getPeopleNumber() > 0;
    }
}
