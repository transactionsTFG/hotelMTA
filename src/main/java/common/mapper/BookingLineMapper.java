package common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import business.booking.BookingDTO;
import business.bookingline.BookingLine;
import business.bookingline.BookingLineDTO;
import business.room.RoomDTO;

@Mapper
public interface BookingLineMapper {
    BookingLineMapper INSTANCE = Mappers.getMapper(BookingLineMapper.class);

    @Mapping(target = "available", source = "bookingLine.available")
    BookingLineDTO toDto(BookingLine bookingLine, RoomDTO roomDTO, BookingDTO bookingDTO);
}
