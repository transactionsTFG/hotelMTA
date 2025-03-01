package business.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomParamsDTO {
    private long id;
    private int number;
    private boolean singleBed;
    private boolean available;
    private int peopleNumber;
    private double dailyPrice;
    private String hotelName;
    private String countryName;
}
