package business.hotel;


import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import business.country.Country;
import business.room.Room;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Version
    private int version;

    @ManyToMany(mappedBy = "hotels")
    private Set<Country> countries;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}
