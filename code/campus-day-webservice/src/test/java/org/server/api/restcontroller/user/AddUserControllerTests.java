package org.server.api.restcontroller.user;

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
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.Random;

import static org.server.RestApiTestConfig.HOST_NAME;
import static org.server.RestApiTestConfig.PORT;
import static org.server.util.RestApiCalls.sendPostRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddUserControllerTests {

    @BeforeAll
    static void setUp() {
        System.out.println("Setup before all tests.");
    }

    @AfterAll
    static void cleanUp() {
        System.out.println("Cleanup after all tests.");
//        UserDAO userDAO = new UserDAO();
//        userDAO.load("testuser1");
//        userDAO.delete();

        // Alle erstelleten Objekte aus der DB löschen
//        UserDAO user = (UserDAO) AbstractDAO.find(UserDAO.class, "testuser1");
//        user.delete();

        UserDAO.deleteAll();
        StationDAO.deleteAll();
    }

    @Test
    @Order(1)
    void testAddUserWithSuccess() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage("testuser1", "0000-0000-0000-0001");
            System.out.println(requestBody);
            String responseBody = sendPostRequest(url, requestBody, "");
            System.out.println(responseBody);

            AddUserResponse registerUserResponse = (AddUserResponse) JsonSerializable.deserialize(responseBody, AddUserResponse.class);
            if ( ! registerUserResponse.isSuccess()) {
                Assertions.fail("adding user failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testAddUserNoSuccessDuplicateUsername() {
        System.out.println("::::: register user test 2 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage("testuser1", "0000-0000-0000-0000");
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddUserResponse responseBody = (AddUserResponse) JsonSerializable.deserialize(responseBodyStr, AddUserResponse.class);
            if ( responseBody.isSuccess() || ! responseBody.isUsernameExits()) {
                Assertions.fail("adding user with duplicate username should have failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testAddUserNoSuccessDuplicateCardId() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage("testuser0", "0000-0000-0000-0001");
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddUserResponse responseBody = (AddUserResponse) JsonSerializable.deserialize(responseBodyStr, AddUserResponse.class);
            if ( responseBody.isSuccess() || ! responseBody.isCardIdExits()) {
                Assertions.fail("adding user with duplicate card-id should have failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testAddUserNoSuccessEmptyFields1() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage("testuser2", null);
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddUserResponse responseBody = (AddUserResponse) JsonSerializable.deserialize(responseBodyStr, AddUserResponse.class);
            if ( responseBody.isSuccess() || responseBody.isCardIdExits() || responseBody.isUsernameExits()) {
                Assertions.fail("adding user with invalid card-id should have failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(5)
    void testAddUserNoSuccessEmptyFields2() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage(null, "0000-0000-0000-0002");
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddUserResponse responseBody = (AddUserResponse) JsonSerializable.deserialize(responseBodyStr, AddUserResponse.class);
            if ( responseBody.isSuccess() || responseBody.isCardIdExits() || responseBody.isUsernameExits()) {
                Assertions.fail("adding user with invalid card-id should have failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(6)
    void testAddUserNoSuccessEmptyFields3() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage(null, "");
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddUserResponse responseBody = (AddUserResponse) JsonSerializable.deserialize(responseBodyStr, AddUserResponse.class);
            if ( responseBody.isSuccess() || responseBody.isCardIdExits() || responseBody.isUsernameExits()) {
                Assertions.fail("adding user with invalid card-id should have failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(7)
    void testAddUserNoSuccessEmptyFields4() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("POST " + url);
            AddUserMessage requestBody = new AddUserMessage("", "");
            System.out.println(requestBody);
            String responseBodyStr = sendPostRequest(url, requestBody, "");
            System.out.println(responseBodyStr);

            AddUserResponse responseBody = (AddUserResponse) JsonSerializable.deserialize(responseBodyStr, AddUserResponse.class);
            if ( responseBody.isSuccess() || responseBody.isCardIdExits() || responseBody.isUsernameExits()) {
                Assertions.fail("adding user with invalid card-id should have failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(8)
    void testAddUserWithSuccessWithStationsAndConnections() {

        String url = "";
        try {

            // 3 Stationen einfügen
            final int STATION_COUNT = 10;
            Random random = new Random();
            for (int i = 0; i < STATION_COUNT; i++) {
                url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
                System.out.println("POST " + url);

                AddStationMessage requestBody = new AddStationMessage(
                        "station-"+(i+1), i+1,
                        LocationDAO.FloorPlanImage.values()[random.nextInt(1, 3)],
                        random.nextInt(-1000, 1000),
                        random.nextInt(-1000, 1000)
                );
//                System.out.println(requestBody);
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

            // Benutzer einfügen
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
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
