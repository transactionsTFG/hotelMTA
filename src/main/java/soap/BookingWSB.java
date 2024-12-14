package soap;

import java.util.List;

import business.booking.BookingAS;
import business.booking.BookingDTO;
import business.booking.BookingTOA;
import business.room.RoomDTO;
import common.consts.WebMethodConsts;
import common.dto.result.Result;
import common.dto.soap.response.BookingSOAP;
import common.dto.soap.response.SoapResponse;
import common.mapper.SoapResponseMapper;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService(serviceName = "BookingWSB")
public class BookingWSB {

    private final BookingAS bookingAS;

    @Inject
    public BookingWSB(final BookingAS bookingAS) {
        this.bookingAS = bookingAS;
    }

    @WebMethod(operationName = WebMethodConsts.MAKE_BOOKING)
    public SoapResponse<BookingSOAP> makeBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        try {
            final Result<BookingTOA> booking = this.bookingAS.createBooking(bookingDTO, rooms);
            return SoapResponseMapper.toSoapResponse(booking.getMessage(), BookingSOAP.toSOAP(booking.getData().getBooking()),
                    booking.isSuccess());
        } catch (Exception e) {
            System.out.println("BookingWSB.makeBooking: " + e.getMessage());
            return SoapResponseMapper.toSoapResponse(e.getMessage(), null, false);
        }
    }

    @WebMethod(operationName = WebMethodConsts.MODIFY_BOOKING)
    public SoapResponse<BookingSOAP> modifyBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        try {
            final Result<BookingTOA> booking = this.bookingAS.createBooking(bookingDTO, rooms);
            return SoapResponseMapper.toSoapResponse(booking.getMessage(), BookingSOAP.toSOAP(booking.getData().getBooking()),
                    booking.isSuccess());
        } catch (Exception e) {
            System.out.println("BookingWSB.modifyBooking: " + e.getMessage());
            return SoapResponseMapper.toSoapResponse(e.getMessage(), null, false);
        }
    }

    @WebMethod(operationName = WebMethodConsts.CANCEL_BOOKING)
    public SoapResponse<Void> cancelBooking(int bookingID) {
        try {
            final Result<Void> booking = this.bookingAS.deleteBooking(bookingID);
            return SoapResponseMapper.toSoapResponse(booking);
        } catch (Exception e) {
            System.out.println("BookingWSB.cancelBooking: " + e.getMessage());
            return SoapResponseMapper.toSoapResponse(e.getMessage(), null, false);
        }
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_BOOKING)
    public SoapResponse<BookingSOAP> searchBooking(int bookingID) {
        try {
            final Result<BookingTOA> booking = this.bookingAS.readBooking(bookingID);
            return SoapResponseMapper.toSoapResponse(booking.getMessage(), BookingSOAP.toSOAP(booking.getData().getBooking()),
                    booking.isSuccess());
        } catch (Exception e) {
            System.out.println("BookingWSB.searchBooking: " + e.getMessage());
            return SoapResponseMapper.toSoapResponse(e.getMessage(), null, false);
        }
    }
}
