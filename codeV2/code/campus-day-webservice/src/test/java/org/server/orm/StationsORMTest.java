package org.server.orm;

import org.junit.jupiter.api.*;
import org.server.orm.classes.LocationDAO;
import org.server.orm.classes.StationDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StationsORMTest {

    /*

--select * from users;
--select * from user_station;
select * from station;

--delete from user_station where id != '';
--delete from station where station_name != '';
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




}
