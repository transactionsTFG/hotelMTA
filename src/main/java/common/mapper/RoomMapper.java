package common.mapper;

import business.room.Room;
import business.room.RoomDTO;
import business.room.RoomParamsDTO;
import common.dto.soap.response.RoomSOAP;

public interface RoomMapper {

    public static RoomDTO toDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .hotelId(room.getHotel() != null ? room.getHotel().getId() : null)
                .number(room.getNumber())
                .singleBed(room.isSingleBed())
                .available(room.isAvailable())
                .peopleNumber(room.getPeopleNumber())
                .dailyPrice(room.getDailyPrice())
                .build();
    }

    public static RoomParamsDTO toRoomParamsDTO(Room room, String hotelName, String countryName) {
        return RoomParamsDTO.builder()
                .id(room.getId())
                .number(room.getNumber())
                .singleBed(room.isSingleBed())
                .available(room.isAvailable())
                .peopleNumber(room.getPeopleNumber())
                .dailyPrice(room.getDailyPrice())
                .hotelName(hotelName)
                .countryName(countryName)
                .build();
    }

    public static RoomSOAP fromDTOToRoomSOAP(RoomDTO roomDTO) {
        return RoomSOAP.builder()
                .id(roomDTO.getId())
                .hotelId(roomDTO.getHotelId())
                .number(roomDTO.getNumber())
                .singleBed(roomDTO.isSingleBed())
                .available(roomDTO.isAvailable())
                .peopleNumber(roomDTO.getPeopleNumber())
                .dailyPrice(roomDTO.getDailyPrice())
                .build();
    }

}
