package org.server.orm;

import org.junit.jupiter.api.*;
import org.server.orm.classes.LocationDAO;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;

import java.util.ArrayList;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserORMTest {

    @BeforeAll
    static void setUp() {
        System.out.println("Setup before all tests.");
    }

    @AfterAll
    static void cleanUp() {
        System.out.println("Cleanup after all tests.");

//            // Erstellte Benutzer löschen
//            UserDAO userDAO = new UserDAO();
//            try{userDAO.load("testuser");}catch(Exception e){}
//            try{userDAO.delete();}catch(Exception e){}
//            try{userDAO.load("testuser1");}catch(Exception e){}
//            try{userDAO.delete();}catch(Exception e){}
//            try{userDAO.load("testuser2");}catch(Exception e){}
//            try{userDAO.delete();}catch(Exception e){}

//            // Erstellte Stationen löschen
//            StationDAO stationDAO = new StationDAO();
//            try{stationDAO.load("station1");}catch(Exception e){}
//            try{stationDAO.delete();}catch(Exception e){}
//            try{stationDAO.load("station2");}catch(Exception e){}
//            try{stationDAO.delete();}catch(Exception e){}
//            try{stationDAO.load("station3");}catch(Exception e){}
//            try{stationDAO.delete();}catch(Exception e){}
//            try{stationDAO.load("station4");}catch(Exception e){}
//            try{stationDAO.delete();}catch(Exception e){}

        StationDAO.deleteAll();
    }

//    // Speichern eines Benutzers ohne Stationen testen
//    @Test
//    @Order(1)
//    public void testInsertUser(){
//
//        UserDAO userDAO = new UserDAO("testuser", "0000-0000-0000-0000");
//        userDAO.persist();
//
//        System.out.println(userDAO);
//
//        UserDAO userDAOLoaded = new UserDAO();
//        userDAOLoaded.load("testuser");
//
//        System.out.println(userDAOLoaded);
//
//        if ( ! userDAO.toString().equals(userDAOLoaded.toString())) {
//            Assertions.fail("Loaded user not equal to inserted user!");
//        }
//    }

//    // Speichern eines Benutzers mit Stationen testen
//    @Test
//    @Order(2)
//    public void testInsertUserWithStations(){
//
//        UserDAO userDAO = new UserDAO("testuser1", "0000-0000-0000-0001");
//
//        // Station erstellen, erstellen und laden
//        StationDAO stationDAO = new StationDAO("station1", 0, LocationDAO.FloorPlanImage.G20, 0, 0);
//        stationDAO.persist();
//        stationDAO.load("station1");
//
//        userDAO.linkStation(stationDAO);
//        userDAO.persist();
//
//        System.out.println(userDAO);
//
//        UserDAO userDAOLoaded = new UserDAO();
//        userDAOLoaded.load(userDAO.getUsername());
//
//        System.out.println(userDAOLoaded);
//
//        if ( ! userDAO.toString().equals(userDAOLoaded.toString())) {
//            Assertions.fail("Loaded user not equal to inserted user!");
//        }
//    }

//    // Laden eines Benutzers ohne Stationen testen
//    @Test
//    @Order(3)
//    public void testloadUser(){
//        UserDAO userDAOLoaded = new UserDAO();
//        userDAOLoaded.load("testuser");
//        System.out.println(userDAOLoaded);
//    }
//
//    @Test
//    @Order(4)
//    public void testLoadUserByRfid() {
//
//        UserDAO userDAO = new UserDAO("testuser2", "0000-0000-0000-0002");
//        userDAO.persist();
//
//        System.out.println(userDAO);
//
//        UserDAO userDAOLoaded = new UserDAO();
//        userDAOLoaded.load(userDAO.getUsername());
//
//        userDAOLoaded = UserDAO.findByRfid("0000-0000-0000-0000");
//        System.out.println(userDAOLoaded);
//
//        userDAO.delete();
//    }
//
//    @Test
//    @Order(5)
//    public void testLoadAllUsers() {
//        ArrayList<UserDAO> users = UserDAO.loadAll();
//        System.out.println(users);
//    }
//
//    @Test
//    @Order(6)
//    public void testLinkStation() {
//
//        // 2 Stationen erstellen
//        StationDAO station1 = new StationDAO();
//        station1.setName("station2");
//
//        StationDAO station2 = new StationDAO();
//        station2.setName("station3");
//
//        System.out.println(station1);
//        System.out.println(station2);
//
////        station1.persist();
////        station2.persist();
//
//        // Orte zur jeweiligen Station hinzufügen
//        station1.setLocation(LocationDAO.FloorPlanImage.G20, 0, 1);
//        station2.setLocation(LocationDAO.FloorPlanImage.G21, 0, 2);
//
//        station1.persist();
//        station2.persist();
//
//        // 2 user erstellen
//        UserDAO user1 = new UserDAO();
//        user1.setUsername("testuser1");
//        user1.setCardRfid(UUID.randomUUID().toString());
//
//        UserDAO user2 = new UserDAO();
//        user2.setUsername("testuser2");
//        user2.setCardRfid(UUID.randomUUID().toString());
//
//        // Stationen den Benutzern zuordnen
//        user1.linkStation(station1);
//        user1.linkStation(station2);
//        user2.linkStation(station1);
//        user2.linkStation(station2);
//
//        // beide user drucken
//        System.out.println(user1);
//        System.out.println(user2);
//
//        // beide user mit join objekten und stationen speichern
//        user1.saveOrUpdate();
//        user2.saveOrUpdate();
//    }
//
//    @Test
//    @Order(7)
//    public void testUnlinkStation() {
//
//        UserDAO userDAO = new UserDAO();
//        userDAO.load("testuser2");
//
//        StationDAO station = new StationDAO();
//        station.load("station2");
//
//        userDAO.removeStation("station2");
//        userDAO.merge();
//    }
//
//    @Test
//    @Order(8)
//    public void testDeleteUser(){
//
//        // Benutzer erstellen
//        UserDAO userDAO = new UserDAO("testuser3", "0000-0000-0000-0003");
//        userDAO.persist();
//
//        // Station laden
//        StationDAO stationDAO = new StationDAO();
//        stationDAO.setName("station4");
//        stationDAO.setLocation(LocationDAO.FloorPlanImage.G20, 0, 1);
//        stationDAO.persist();
//
//        // Station verbinden
//        userDAO.linkStation(stationDAO);
//
//        System.out.println(userDAO);
//
//        // Benutzer updaten / Verbindung speichern
//        userDAO.merge();
//
//        // Benutzer löschen
//        userDAO.delete();
//
//        UserDAO userDAOLoaded = new UserDAO();
//        userDAOLoaded.load("testuser");
//
//        if (userDAOLoaded.getUsername() == null){
//            Assertions.fail("User did not get deleted!");
//        }
//
//        StationDAO stationDAOLoaded = new StationDAO();
//        stationDAOLoaded.load("station4");
//        if ( stationDAOLoaded.getName() == null ){
//            Assertions.fail("Station got deleted when deleting the associated user!");
//        }
//    }
//
//    @Test
//    @Order(9)
//    public void testCheckStation() {
//
//        UserDAO userDAO = new UserDAO();
//        userDAO.load("testuser2");
//
//        userDAO.checkStation("station3");
//        userDAO.merge();
//    }
//
//    @Test
//    @Order(10)
//    public void testUncheckStation() {
//
//        UserDAO userDAO = new UserDAO();
//        userDAO.load("testuser2");
//
//        userDAO.uncheckStation("station3");
//        userDAO.merge();
//    }
//
////    @Test
////    @Order(11)
////    public void testUpdateUsername() {
////
////
////    }
//
//    @Test
//    @Order(12)
//    public void testDeleteAllUsers() {
//        UserDAO.deleteAll();
//    }

    @Test
    @Order(4)
    public void testLoadUserByRfid() {

        UserDAO userDAOLoaded = UserDAO.findByRfid("0000-0001");
        System.out.println(userDAOLoaded);

        userDAOLoaded = new UserDAO();
        userDAOLoaded.load("testuser-1");

        userDAOLoaded = UserDAO.findByRfid("0000-0001");
        System.out.println(userDAOLoaded);



    }
}
