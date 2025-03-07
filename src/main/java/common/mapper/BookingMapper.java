package common.mapper;

import business.booking.Booking;
import business.booking.BookingDTO;
import common.dto.soap.response.BookingSOAP;

public interface BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .withBreakfast(booking.isWithBreakfast())
                .peopleNumber(booking.getPeopleNumber())
                .customerId(booking.getCustomer().getId())
                .available(booking.isAvailable())
                .totalPrice(booking.getTotalPrice())
                .build();
    }

    public static BookingSOAP fromDTOToSOAP(BookingDTO bookingDTO) {
        return BookingSOAP.builder()
                .id(bookingDTO.getId())
                .withBreakfast(bookingDTO.isWithBreakfast())
                .peopleNumber(bookingDTO.getPeopleNumber())
                .customerId(bookingDTO.getCustomerId())
                .available(bookingDTO.isAvailable())
                .totalPrice(bookingDTO.getTotalPrice())
                .build();
    }

    public static BookingSOAP fromSOAPToEntity(BookingDTO b) {
        return BookingSOAP.builder()
                .id(b.getId())
                .withBreakfast(b.isWithBreakfast())
                .peopleNumber(b.getPeopleNumber())
                .available(b.isAvailable())
                .totalPrice(b.getPeopleNumber())
                .build();
    }
}
