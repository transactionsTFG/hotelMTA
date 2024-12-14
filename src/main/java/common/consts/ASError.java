package common.consts;

public class ASError {

    // BOOKING
    public static final String BOOKING_NOT_FOUND = "Booking not found";

    // ROOM
    public static final String ROOM_NOT_FOUND = "Room not found";
    public static final String NULL_ROOM = "Room cannot be null";
    public static final String NEGATIVE_ROOM_NUMBER = "Room number cannot be negative";
    public static final String NEGATIVE_PEOPLE_NUMBER = "People number cannot be negative";
    public static final String ACTIVE_ROOM = "Room is active";
    public static final String NON_ACTIVE_ROOM = "Room is not active";

    // CUSTOMER
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String NULL_CUSTOMER = "Customer cannot be null";
    public static final String INVALID_DNI = "Invalid DNI syntax";
    public static final String INVALID_EMAIL = "Invalid email syntax";
    public static final String INVALID_PHONE = "Invalid phone syntax";
    public static final String ACTIVE_CUSTOMER = "Customer is active";
    public static final String NON_ACTIVE_CUSTOMER = "Customer is not active";

    private ASError() {
    }
}
