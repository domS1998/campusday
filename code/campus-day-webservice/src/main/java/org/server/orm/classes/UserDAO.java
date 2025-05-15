package org.server.orm.classes;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.server.orm.AbstractDAO;
import org.server.orm.HibernateApi;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "users")
@Entity
@NamedQuery(
        name = "User.findByRfid",
        query = "SELECT u FROM UserDAO u WHERE u.cardRfid = :rfid"
)
@NamedQuery(
        name = "User.findAll",
        query =
            """
            SELECT DISTINCT u FROM UserDAO u 
            LEFT JOIN FETCH u.userStations
            """
)
// Benutzer
public class UserDAO extends AbstractDAO {

//    // unveränderliche Id, Primärschlüssel
//    @Id
//    @Column(name = "id", nullable = false)
//    private String id = UUID.randomUUID().toString();

    // variabler Benutzername
//    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // rfid der Chipkarte
    @Id
    @Column(name = "card_rfid", nullable = false, unique = true)
    private String cardRfid;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserStationDAO> userStations = new HashSet<>();

    public UserDAO(String username, String cardRfid) {
        this.username = username;
        this.cardRfid = cardRfid;
    }

    // Station zu Benutzer hinzufügen
    public void linkStation(StationDAO station) {
        UserStationDAO userStation = new UserStationDAO();
        // user mit user_station und station verbinden
        this.getUserStations().add(userStation);
        userStation.setUser(this);
        userStation.setStation(station);
        station.getUserStations().add(userStation);
    }

    public void removeStation(String stationName) {

        UserStationDAO userStationTarget = null;

        for (UserStationDAO userStation : this.getUserStations()) {
            if (userStation.getStation().getName().equals(stationName)) {
                userStationTarget = userStation;
            }
        }

        if (userStationTarget == null) {
            return;
        }

        this.getUserStations().remove(userStationTarget);

        StationDAO station = new StationDAO();
        station.load(stationName);
        station.getUserStations().remove(userStationTarget);
        station.merge();
    }

    // alle Stationen aus einer Liste zu dem Benutzer hinzufügen
    public void linkStations(ArrayList<StationDAO> stationList) {
        for (StationDAO station : stationList) {
            this.linkStation(station);
        }
    }

    // alle vorhandenen Benutzer laden
    public static ArrayList<UserDAO> loadAll() {

        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }
        
//        List<UserDAO> users = session.createQuery("FROM UserDAO", UserDAO.class).list();

        List<UserDAO> users = session.createNamedQuery("User.findAll", UserDAO.class).list();

        tx.commit();

//        System.out.println(users);

//        for (UserDAO user : users) {
//            user.load(user.getUsername());
//            System.out.println(user);
//        }

//        session.close();

        return new ArrayList<>(users);
    }

    // alle vorhandenen Stationen löschen
    public static void deleteAll () {
        ArrayList<UserDAO> users = UserDAO.loadAll();
        for (UserDAO user : users) {
            user.delete();
        }
    }

    // Benutzer über rfid laden
    public static UserDAO findByRfid(String rfid) {
        UserDAO result  = null;
        Session session = null;
        Transaction tx  = null;
        try {
            session = HibernateApi.getInstance().getSession();
            if (session == null || !session.isOpen()) {
                session = HibernateApi.getInstance().factory.openSession(); // implement this
            }

            tx = session.getTransaction();
            if (tx == null || !tx.isActive()) {
                tx = session.beginTransaction();
            }

            Query<UserDAO> query = session.createNamedQuery(
                "User.findByRfid", UserDAO.class
            );
            query.setParameter("rfid", rfid);
            result =  query.uniqueResult();

            tx.commit();
//            session.close();
        }
        catch (Exception e) {
            System.out.println("Rolling back transaction due to: " + e.getMessage());
            assert tx != null;
            tx.rollback();  // ✅ Ensure rollback is handled
        }
        return result;
    }

    public void checkStation(String stationName) {
        for (UserStationDAO userStation : this.getUserStations()) {
            if (userStation.getStation().getName().equals(stationName)) {
                userStation.setCompleted(true);
            }
        }
    }

    public void uncheckStation(String stationName) {
        for (UserStationDAO userStation : this.getUserStations()) {
            if (userStation.getStation().getName().equals(stationName)) {
                userStation.setCompleted(false);
            }
        }
    }



}