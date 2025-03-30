package org.server.orm;

import org.junit.jupiter.api.Test;
import org.server.exceptions.*;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.server.orm.classes.UserStationDao;

import java.util.UUID;

public class StationsORMTest {
    /*
        select * from users;
        select * from user_station;
        select * from station;

        --delete from user_station where id != '';
        --delete from station where id != '';
        --delete from users where id != '';
    */

    @Test
    public void testCreateStation() throws SaveObjectException, DuplicateUserException {

        // 2 Stationen erstellen
        StationDAO station1 = new StationDAO();
        station1.setName("station-1");

        StationDAO station2 = new StationDAO();
        station2.setName("station-2");

        // 2 user erstellen
        UserDAO user1 = new UserDAO();
        user1.setUsername("testuser1");
        user1.setCardRfid(UUID.randomUUID().toString());

        UserDAO user2 = new UserDAO();
        user2.setUsername("testuser2");
        user2.setCardRfid(UUID.randomUUID().toString());

        // 4 join objekte erstellen
        UserStationDao userStation1 = new UserStationDao();
        UserStationDao userStation2 = new UserStationDao();
        UserStationDao userStation3 = new UserStationDao();
        UserStationDao userStation4 = new UserStationDao();

        // user1 mit user_station und station verbinden
        user1.getUserStations().add(userStation1);
        userStation1.setUser(user1);
        userStation1.setStation(station1);
        station1.getUserStations().add(userStation1);

        user1.getUserStations().add(userStation2);
        userStation2.setUser(user1);
        userStation2.setStation(station1);
        station2.getUserStations().add(userStation1);

        // user2 mit user_station und station verbinden
        user2.getUserStations().add(userStation3);
        userStation3.setUser(user2);
        userStation3.setStation(station1);
        station1.getUserStations().add(userStation3);

        user2.getUserStations().add(userStation4);
        userStation4.setUser(user2);
        userStation4.setStation(station2);
        station2.getUserStations().add(userStation4);

        // beide user drucken
        System.out.println(user1);
        System.out.println(user2);

        // beide user mit join objekten und stationen speichern
        user1.saveOrUpdate();
        user2.saveOrUpdate();







    }
}
