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

    // generische ID
    @Id
    @Column(name = "id")
    private String id = UUID.randomUUID().toString();

    // Referenz / Fremdschlüssel auf Benutzertabelle
    @JsonBackReference // Zeiger beim Serialisieren zu JSON nicht dereferenzieren
    @EqualsAndHashCode.Exclude
    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "user_id", nullable = false)
    private UserDAO user;

    // Referenz / Fremdschlüssel auf Stationen
    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "station_id", nullable = false)
    private StationDAO station;

    // Flag um die Station als abgeschlossen zu markieren
    @Column(name = "completed")
    private boolean completed = false;
}
