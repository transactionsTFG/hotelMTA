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

    // public static RoomDTO toDTO(Room room) {
    //     return RoomDTO.builder()
    //             .id(room.getId())
    //             .hotelId(room.getHotel() != null ? room.getHotel().getId() : null)
    //             .number(room.getNumber())
    //             .singleBed(room.isSingleBed())
    //             .available(room.isAvailable())
    //             .peopleNumber(room.getPeopleNumber())
    //             .dailyPrice(room.getDailyPrice())
    //             .build();
    // }

    RoomParamsDTO toRoomParamsDTO(Room room, String hotelName, String countryName);

    // public static RoomParamsDTO toRoomParamsDTO(Room room, String hotelName, String countryName) {
    //     return RoomParamsDTO.builder()
    //             .id(room.getId())
    //             .number(room.getNumber())
    //             .singleBed(room.isSingleBed())
    //             .available(room.isAvailable())
    //             .peopleNumber(room.getPeopleNumber())
    //             .dailyPrice(room.getDailyPrice())
    //             .hotelName(hotelName)
    //             .countryName(countryName)
    //             .build();
    // }

    RoomSOAP fromDTOToRoomSOAP(RoomDTO roomDTO);
    
    // public static RoomSOAP fromDTOToRoomSOAP(RoomDTO roomDTO) {
    //     return RoomSOAP.builder()
    //             .id(roomDTO.getId())
    //             .hotelId(roomDTO.getHotelId())
    //             .number(roomDTO.getNumber())
    //             .singleBed(roomDTO.isSingleBed())
    //             .available(roomDTO.isAvailable())
    //             .peopleNumber(roomDTO.getPeopleNumber())
    //             .dailyPrice(roomDTO.getDailyPrice())
    //             .build();
    // }

}
