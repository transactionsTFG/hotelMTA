package business.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomParamsDTO {
    private int id;
    private int number;
    private boolean occupied;
    private boolean singleBed;
    private boolean active;
    private int peopleNumber;
    private String hotelName;
    private String countryName; 
}
