package business.room;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import common.consts.ASError;
import common.dto.result.Result;
import common.exception.ASException;
import common.exception.RoomASException;

@Stateless
public class RoomASImp implements RoomAS {

    private EntityManager em;

    public RoomASImp(){}

    @Inject
    public RoomASImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public Result<RoomDTO> createRoom(RoomDTO roomDTO) {

        this.isValid(roomDTO);

        TypedQuery<Room> query = em.createNamedQuery("business.room.getByRoomNumber", Room.class);
        query.setParameter("number", roomDTO.getNumber());
        Room room = query.getResultList().isEmpty() ? null : query.getResultList().get(0); 
        
        if (room == null) {
            room = new Room(roomDTO);
            em.persist(room);
            return Result.success(room.toDTO());
        }

        if (room.isActive()) {
            throw new ASException(ASError.ACTIVE_ROOM);
        }

        room.setActive(true);
        room.setNumber(roomDTO.getNumber());
        room.setOccupied(roomDTO.isOccupied());
        room.setPeopleNumber(roomDTO.getPeopleNumber());
        room.setSingleBed(roomDTO.isSingleBed());
        em.persist(room);
        return Result.success(room.toDTO());

    }

    @Override
    public Result<RoomDTO> readRoom(int roomId) {
        Room room = this.em.find(Room.class, roomId, LockModeType.OPTIMISTIC);
        if (room == null)
            throw new RoomASException(ASError.ROOM_NOT_FOUND);

        return Result.success(room.toDTO());

    }

    @Override
    public Result<RoomDTO> readRoomByNumber(int number) {
        Room room = this.em.find(Room.class, number, LockModeType.NONE);
        if (room == null)
            throw new RoomASException(ASError.ROOM_NOT_FOUND);

        return Result.success(room.toDTO());
    }

    @Override
    public Result<Void> deleteRoom(int id) {

        Room room = this.em.find(Room.class, id);

        if (room == null) {
            throw new RoomASException(ASError.ROOM_NOT_FOUND);
        }

        if (!room.isActive()) {
            throw new RoomASException(ASError.NON_ACTIVE_ROOM);
        }

        room.setActive(false);

        return Result.success(null);

    }

  

    @Override
    public List<RoomParamsDTO> readRooms(String hotelName, String countryName) {
        TypedQuery<Object[]> query = this.em.createNamedQuery("business.room.getAllRoomsWithParams", Object[].class);
        query.setParameter("hotelName", hotelName != null ? hotelName : null);
        query.setParameter("countryName", countryName != null ? countryName : null);
        List<Object[]> results = query.getResultList();
        return results.stream()
            .map(result -> new RoomParamsDTO(
                    ((Room) result[0]).getId(),
                    ((Room) result[0]).getNumber(),
                    ((Room) result[0]).isOccupied(),
                    ((Room) result[0]).isSingleBed(),
                    ((Room) result[0]).isActive(),
                    ((Room) result[0]).getPeopleNumber(),
                    result[1] != null ? (String) result[1] : null,
                    result[2] != null ? (String) result[2] : null
            ))
        .toList();
    }
    
    private void isValid(RoomDTO room) throws ASException {
        if (room == null)
            throw new RoomASException(ASError.NON_EXISTENT_ROOM);

        if (room.getNumber() < 0)
            throw new RoomASException(ASError.INVALID_ROOM_NUMBER);

        if (room.getPeopleNumber() <= 0)
            throw new RoomASException(ASError.INVALID_PEOPLE_NUMBER);
    }
}
