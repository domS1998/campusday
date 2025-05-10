package org.server.orm.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.server.orm.AbstractDAO;
import org.server.orm.HibernateApi;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "station")
@NamedQuery(
        name = "Station.findByName",
        query = "SELECT S FROM StationDAO S WHERE S.name = :name"
)
@NamedQuery(
        name = "Station.findByNumber",
        query = "SELECT S FROM StationDAO S WHERE S.number = :number"
)
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
    @OneToMany(mappedBy = "station", /*cascade = CascadeType.ALL, */orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserStationDAO> userStations = new HashSet<>();

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

        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }
        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<StationDAO> stations = session.createQuery("FROM StationDAO", StationDAO.class).list();

        tx.commit();
//        session.close();

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

    // Station über Namen laden
    public static StationDAO findByName(String stationName){
        StationDAO result = null;
        Session session   = null;
        Transaction tx    = null;

        try {
            session = HibernateApi.getInstance().getSession();

            if (session == null || !session.isOpen()) {
                session = HibernateApi.getInstance().factory.openSession(); // implement this
            }

            tx = session.getTransaction();
            if (tx == null || !tx.isActive()) {
                tx = session.beginTransaction();
            }

            Query<StationDAO> query = session.createNamedQuery(
                "Station.findByName", StationDAO.class
            );
            query.setParameter("name", stationName);
            result = query.uniqueResult();

            tx.commit();
//            session.close();
        }
        catch (Exception e) {
            System.out.println("Rolling back transaction due to: " + e.getMessage());
            tx.rollback();  // ✅ Ensure rollback is handled
        }
        return result;
    }

    // Station über Namen laden
    public static StationDAO findByNumber(String stationNumber){
        StationDAO result = null;
        Session session   = null;
        Transaction tx    = null;
        try {
            session = HibernateApi.getInstance().getSession();

            if (session == null || !session.isOpen()) {
                session = HibernateApi.getInstance().factory.openSession(); // implement this
            }

            tx = session.getTransaction();
            if (tx == null || !tx.isActive()) {
                tx = session.beginTransaction();
            }
            Query<StationDAO> query = session.createNamedQuery(
                "Station.findByNumber", StationDAO.class
            );
            query.setParameter("number", stationNumber);
            result = query.uniqueResult();

            tx.commit();
//            session.close();
        }
        catch (Exception e) {
            System.out.println("Rolling back transaction due to: " + e.getMessage());
            session.getTransaction().rollback();  // ✅ Ensure rollback is handled
        }
        return result;
    }
}