package ucm.tfg.hotelMTA.business.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerDTO {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String dni;
    private boolean active;
}
