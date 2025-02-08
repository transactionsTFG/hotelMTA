package soap;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import business.booking.BookingAS;
import business.booking.BookingDTO;
import business.booking.BookingTOA;
import business.room.RoomDTO;
import common.consts.WebMethodConsts;
import common.dto.result.Result;
import common.dto.soap.response.BookingSOAP;
import common.dto.soap.response.SoapResponse;
import common.mapper.SoapResponseMapper;
import weblogic.wsee.wstx.wsat.Transactional;

@WebService(serviceName = "BookingWSB")
public class BookingWSB {

    private final BookingAS bookingAS;

    @Inject
    public BookingWSB(final BookingAS bookingAS) {
        this.bookingAS = bookingAS;
    }

    @WebMethod(operationName = WebMethodConsts.MAKE_BOOKING)
    @Transactional(version = Transactional.Version.WSAT12, value = Transactional.TransactionFlowType.MANDATORY)
    public SoapResponse<BookingSOAP> makeBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        final Result<BookingTOA> booking = this.bookingAS.createBooking(bookingDTO, rooms);
        return SoapResponseMapper.toSoapResponse(booking.getMessage(), BookingSOAP.toSOAP(booking.getData().getBooking()),
                    booking.isSuccess());
    }

    @WebMethod(operationName = WebMethodConsts.MODIFY_BOOKING)
    @Transactional(version = Transactional.Version.WSAT12, value = Transactional.TransactionFlowType.MANDATORY)
    public SoapResponse<BookingSOAP> modifyBooking(BookingDTO bookingDTO, List<RoomDTO> rooms) {
        final Result<BookingTOA> booking = this.bookingAS.createBooking(bookingDTO, rooms);
        return SoapResponseMapper.toSoapResponse(booking.getMessage(), BookingSOAP.toSOAP(booking.getData().getBooking()),
                    booking.isSuccess());
      
    }

    @WebMethod(operationName = WebMethodConsts.CANCEL_BOOKING)
    @Transactional(version = Transactional.Version.WSAT12, value = Transactional.TransactionFlowType.MANDATORY)
    public SoapResponse<Void> cancelBooking(int bookingID) {
        final Result<Void> booking = this.bookingAS.deleteBooking(bookingID);
        return SoapResponseMapper.toSoapResponse(booking);
    }

    @WebMethod(operationName = WebMethodConsts.SEARCH_BOOKING)
    @Transactional(version = Transactional.Version.WSAT12, value = Transactional.TransactionFlowType.MANDATORY)
    public SoapResponse<BookingSOAP> searchBooking(int bookingID) {
        final Result<BookingTOA> booking = this.bookingAS.readBooking(bookingID);
        return SoapResponseMapper.toSoapResponse(booking.getMessage(), BookingSOAP.toSOAP(booking.getData().getBooking()),
                    booking.isSuccess());
        
    }
}
