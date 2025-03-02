package common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import business.room.Room;
import business.room.RoomDTO;
import business.room.RoomParamsDTO;
import common.dto.soap.response.RoomSOAP;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "hotelId", source = "hotel.id")
    RoomDTO toDTO(Room room);

    RoomParamsDTO toRoomParamsDTO(Room room, String hotelName, String countryName);

    RoomSOAP fromDTOToRoomSOAP(RoomDTO roomDTO);

}
