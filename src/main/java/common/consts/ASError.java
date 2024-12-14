package common.consts;

public class ASError {

    // BOOKING
    public static final String BOOKING_NOT_FOUND = "Booking not found";
    public static final String INVALID_NUMBER_OF_NIGHTS = "Number of nights cannot be negative";
    public static final String INVALID_CUSTOMER_ID = "Customer id cannot be negative";
    public static final String INVALID_AGENCY_NAME = "Invalid agency name syntax";
    public static final String INVALID_DATE = "Invalid date syntax";
    public static final String NON_EXISTENT_BOOKING = "Booking does not exist";
    public static final String INVALID_ROOM_LIST = "Invalid room list";
    public static final String ROOM_LIST_HAS_NON_ACTIVE_OR_NON_EXISTENT_ROOMS = "Room list has non active or non existent rooms";
    public static final String NON_ACTIVE_BOOKING = "Non active booking";

    // ROOM
    public static final String ROOM_NOT_FOUND = "Room not found";
    public static final String NON_EXISTENT_ROOM = "Room does not exist";
    public static final String INVALID_ROOM_NUMBER = "Room number cannot be negative";
    public static final String INVALID_PEOPLE_NUMBER = "People number cannot be negative";
    public static final String ACTIVE_ROOM = "Room is active";
    public static final String NON_ACTIVE_ROOM = "Room is not active";

    // CUSTOMER
    public static final String NON_EXISTENT_CUSTOMER = "Customer not found";
    public static final String INVALID_DNI = "Invalid DNI syntax";
    public static final String INVALID_EMAIL = "Invalid email syntax";
    public static final String INVALID_PHONE = "Invalid phone syntax";
    public static final String ACTIVE_CUSTOMER = "Customer is active";
    public static final String NON_ACTIVE_CUSTOMER = "Customer is not active";

    private ASError() {
    }
}
