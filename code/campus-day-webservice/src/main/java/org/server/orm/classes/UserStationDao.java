package org.server.orm.classes;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.server.orm.AbstractDAO;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_station")
public class UserStationDao extends AbstractDAO {

    @Id
    @Column(name = "id")
    private String id = UUID.randomUUID().toString();

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDAO user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id", nullable = false)
    private StationDAO station;

    @Column(name = "completed")
    private boolean completed = false;
}
