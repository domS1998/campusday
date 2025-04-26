package org.server.api.restcontroller.station;

import org.junit.jupiter.api.*;
import org.server.api.JsonSerializable;
import org.server.api.messages.station.AddStationMessage;
import org.server.api.messages.station.AddStationResponse;
import org.server.api.messages.user.AddUserMessage;
import org.server.api.messages.user.AddUserResponse;
import org.server.orm.AbstractDAO;
import org.server.orm.classes.LocationDAO;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;

import java.util.Random;

import static org.server.RestApiTestConfig.HOST_NAME;
import static org.server.RestApiTestConfig.PORT;
import static org.server.util.RestApiCalls.sendPostRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddStationControllerTests {

    @BeforeAll
    static void setUp() {
        System.out.println("Setup before all tests.");
    }

    @AfterAll
    static void cleanUp() {
        System.out.println("Cleanup after all tests.");
        // Alle erstelleten Objekte aus der DB löschen
//        StationDAO station = (StationDAO) AbstractDAO.find(StationDAO.class, "station-1");
//        station.delete();
      //  StationDAO.deleteAll();
       // UserDAO.deleteAll();
    }

    @Test
    @Order(1)
    void testAddStationWithSuccessNoUserConnections() {
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
            System.out.println("POST " + url);

            AddStationMessage requestBody = new AddStationMessage(
                    "station1", 1,
                    LocationDAO.FloorPlanImage.G20,
                    20,
                    -100
            );
            System.out.println(requestBody);

            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddStationResponse responseBody = (AddStationResponse) JsonSerializable.deserialize(responseBodyStr, AddStationResponse.class);

            assert responseBody != null;
            if (    ! responseBody.isNameOk()
                 || ! responseBody.isNumberOk()
                 || ! responseBody.isLocationOk()
                 || !responseBody.isSuccess())
            {
                Assertions.fail("adding station failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testAddStationNoSuccessNoUserConnections() {
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
            System.out.println("POST " + url);
            AddStationMessage requestBody = new AddStationMessage(
                    "station1",
                    1,
                    LocationDAO.FloorPlanImage.G20,
                    20,
                    -100
            );
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddStationResponse responseBody = (AddStationResponse) JsonSerializable.deserialize(responseBodyStr, AddStationResponse.class);
            assert responseBody != null;
            if ( responseBody.isNameOk() || ! responseBody.isNumberOk() || ! responseBody.isLocationOk() || responseBody.isSuccess()) {
                Assertions.fail("adding station succeeded despite duplicate name !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testAddStationNoSuccessUsedNumber() {
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
            System.out.println("POST " + url);
            AddStationMessage requestBody = new AddStationMessage("station2", 1, LocationDAO.FloorPlanImage.G20, 20, -100);
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddStationResponse responseBody = (AddStationResponse) JsonSerializable.deserialize(responseBodyStr, AddStationResponse.class);
            assert responseBody != null;
            if (    ! responseBody.isNameOk()
                 ||   responseBody.isNumberOk()
                 || ! responseBody.isLocationOk()
                 ||   responseBody.isSuccess()
            ) {
                Assertions.fail("adding station succeeded despite duplicate number !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testAddStationNoSuccessInvalidLocation1() {
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
            System.out.println("POST " + url);
            AddStationMessage requestBody = new AddStationMessage("station3", 1, null, 20, -100);
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddStationResponse responseBody = (AddStationResponse) JsonSerializable.deserialize(responseBodyStr, AddStationResponse.class);
            assert responseBody != null;
            if (    responseBody.isLocationOk()
                 || responseBody.isSuccess()
            ) {
                Assertions.fail("adding station succeeded despite duplicate number !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(5)
    void testAddStationWithSuccessWithUsersAndConnections() {

        String url = "";
        try {
            // 5 Benutzer einfügen
            final int USER_COUNT = 5;
            for (int i = 0; i < USER_COUNT; i++) {
                url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
                System.out.println("POST " + url);
                AddUserMessage requestBody = new AddUserMessage("testuser-"+(i+1), "0000-0000-0000-0000"+(i+1));
                System.out.println(requestBody);
                String responseBody = sendPostRequest(url, requestBody, "");
                System.out.println(responseBody);
                AddUserResponse registerUserResponse = (AddUserResponse) JsonSerializable.deserialize(responseBody, AddUserResponse.class);
                if ( ! registerUserResponse.isSuccess()) {
                    Assertions.fail("adding user failed !");
                }
            }

            // 5 Stationen einfügen
            final int STATION_COUNT = 5;
            Random random = new Random();
            for (int i = 0; i < STATION_COUNT; i++) {
                url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
                System.out.println("POST " + url);

                AddStationMessage requestBody = new AddStationMessage(
                        "station-"+(i+1), i+101,
                        LocationDAO.FloorPlanImage.values()[random.nextInt(1, 3)],
                        random.nextInt(-1000, 1000),
                        random.nextInt(-1000, 1000)
                );
                System.out.println(requestBody);
                String responseBodyStr = sendPostRequest(url, requestBody, "");
                System.out.println(responseBodyStr);
                AddStationResponse responseBody = (AddStationResponse) JsonSerializable.deserialize(responseBodyStr, AddStationResponse.class);

                assert responseBody != null;
                if (    ! responseBody.isNameOk()
                     || ! responseBody.isNumberOk()
                     || ! responseBody.isLocationOk()
                     || ! responseBody.isSuccess())
                {
                    Assertions.fail("adding station failed !");
                }
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

}
