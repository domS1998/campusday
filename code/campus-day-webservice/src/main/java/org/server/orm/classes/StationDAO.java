package org.server.orm.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.server.orm.AbstractDAO;
import org.server.orm.HibernateSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "station")
@Entity
// Verbindungsobjekt für die Join Tabellen bei n:m Beziehungen;
//  wird explizit als 2x 1:n implementiert, da bei n:m
//  in Hibernate/JPA keine zusätzlichen Spalten außer eine Reihenfolge
//  für die Join-Tabelle definiert werden können.
public class StationDAO extends AbstractDAO {

//    @Id
//    @Column(name = "id", nullable = false)
//    private String station_id = UUID.randomUUID().toString();

    // veränderbarer Name bzw. Label der Station
    @Id
    @Column(name = "station_name", nullable = false)
    private String name;

    // Nummer auf Controller
    @Column(name = "number", nullable = false)
    private int number;

    @Embedded
    @Column(name = "location", nullable = true)
    private LocationDAO location;

    // Referenz auf Verbindungsobjekte zu Benutzern
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserStationDao> userStations = new HashSet<>();

    public StationDAO (String name, int number, LocationDAO location) {
        this.name = name;
        this.number = number;
        this.location = location;
    }

    public StationDAO (String name, int number, LocationDAO.FloorPlanImage planImage, int xCoordinate, int yCoordinate) {
        this.name = name;
        this.number = number;
        this.location = new LocationDAO(planImage, xCoordinate, yCoordinate);
    }

    // alle vorhandenen Stationen laden
    public static ArrayList<StationDAO> loadAll() {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        List<StationDAO> stations = HibernateSession.getInstance().getSession().createQuery("FROM StationDAO", StationDAO.class).list();
        HibernateSession.getInstance().getSession().getTransaction().commit();
        return new ArrayList<>(stations);
    }

    // alle vorhandenen Stationen löschen
    public static void deleteAll () {
        ArrayList<StationDAO> stations = StationDAO.loadAll();
        for (StationDAO station : stations) {
            station.delete();
        }
    }

    // Standort einer Station zuordnen
    public void setLocation(LocationDAO.FloorPlanImage floorPlanImageNum, int xCoordinate, int yCoordinate){
        this.location = new LocationDAO(floorPlanImageNum, xCoordinate, yCoordinate);
    }
}