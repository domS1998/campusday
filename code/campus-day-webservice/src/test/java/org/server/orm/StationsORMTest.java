package org.server.orm;

import org.junit.jupiter.api.*;
import org.server.orm.classes.LocationDAO;
import org.server.orm.classes.StationDAO;
import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StationsORMTest {
    /*

--select * from users;
--select * from user_station;
select * from station;

--delete from user_station where station_name != '';
--delete from station where id != '';
--delete from users where username != '';
--drop table user_station;
--drop table users;
--drop table station;

    */

    @BeforeAll
    static void setUp() {
        System.out.println("Setup before all tests.");
    }

    @AfterAll
    static void cleanUp() {
        System.out.println("Cleanup after all tests.");
        // Perform cleanup (e.g., delete test data, close connections)
    }


    // Speichern einer einzelnen Station testen
    @Test
    @Order(1)
    public void testInsertStation() {

        StationDAO stationDAO = new StationDAO("station-test-1", 0, new LocationDAO(LocationDAO.FloorPlanImage.G20, 0, 0));
        stationDAO.persist();

        System.out.println("Station inserted: \n" + stationDAO);

        StationDAO stationDAOLoaded = new StationDAO();
        stationDAOLoaded.load(stationDAO.getName());

        System.out.println("Station loaded: \n" + stationDAOLoaded);

        if ( ! stationDAO.toString().equals(stationDAOLoaded.toString())) {
            Assertions.fail("Loaded station is not the same as inserted station!");
        }
    }

    // Speichern einer einzelnen zweiten Station testen mit anderem Konstruktor
    @Test
    @Order(2)
    public void testInsertStation2() {

        StationDAO stationDAO = new StationDAO("station-test-2", 0, LocationDAO.FloorPlanImage.G21, 0, 0);
        stationDAO.persist();

        System.out.println("Station inserted: \n" + stationDAO);

        StationDAO stationDAOLoaded = new StationDAO();
        stationDAOLoaded.load(stationDAO.getName());

        System.out.println("Station loaded: \n" + stationDAOLoaded);

        if ( ! stationDAO.toString().equals(stationDAOLoaded.toString())) {
            Assertions.fail("Loaded station is not the same as inserted station!");
        }
    }

    // Speichern mehrerer Station testen
    @Test
    @Order(3)
    public void testInsertStations() {

    }

    // Laden einer gespeicherten Station testen
    @Test
    @Order(4)
    void testLoadStation(){

        StationDAO stationDAO = new StationDAO("station-test-3", 0, LocationDAO.FloorPlanImage.G22, 0, 0);
        stationDAO.persist();

        System.out.println("Station inserted: \n" + stationDAO);

        StationDAO stationDAOLoaded = new StationDAO();
        stationDAOLoaded.load(stationDAO.getName());

        System.out.println("Station loaded: \n" + stationDAOLoaded);

        if ( ! stationDAO.toString().equals(stationDAOLoaded.toString())) {
            Assertions.fail("Loaded station is not the same as inserted station!");
        }
    }

    // Alle Stationen im System laden testen
    @Test
    @Order(5)
    void testLoadAllStations(){
        ArrayList<StationDAO> stations = StationDAO.loadAll();
        System.out.println(stations);
    }

    @Test
    @Order(6)
    public void testUpdateLocation() {

        StationDAO stationDAO = new StationDAO();
        stationDAO.load("station-test-2");

        System.out.println("Station before setting location:\n" + stationDAO);

        stationDAO.setLocation(LocationDAO.FloorPlanImage.G22, 1, 2);

        System.out.println("Station after adding new location:\n" + stationDAO);

        try {
            stationDAO.merge();
        }
        catch (Exception e) {
            Assertions.fail("Failed to update location of station " + stationDAO.getName() +":\n" + e.getMessage());
        }
    }

    @Test
    @Order(7)
    void testDeleteStation(){
        StationDAO stationDAO = new StationDAO();
        stationDAO.load("station-test-1");

        System.out.println(stationDAO);

        stationDAO.delete();

        StationDAO stationDAOLoaded = new StationDAO();

        try {
            stationDAOLoaded.load(stationDAO.getName());
            Assertions.fail("Loaded station did not get deleted!");
        }
        catch (Exception e) {}

        System.out.println(stationDAOLoaded);
    }

    @Test
    @Order(8)
    void testDeleteAllStations(){
        StationDAO.deleteAll();
    }

}
