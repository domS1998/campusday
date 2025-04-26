package org.server.api.restcontroller.station;

import org.junit.jupiter.api.*;
import org.server.api.JsonSerializable;
import org.server.api.messages.station.*;
import org.server.api.messages.user.*;
import org.server.orm.classes.LocationDAO;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;

import java.util.Random;

import static org.server.RestApiTestConfig.*;
import static org.server.util.RestApiCalls.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UpdateStationControllerTests {

//    @BeforeAll
//    static void setUp() {
//        System.out.println("Setup before all tests.");
//        String url = "";
//        try {
//
//            // 5 Stationen einfügen
//            final int STATION_COUNT = 24;
//            Random random = new Random();
//            for (int i = 0; i < STATION_COUNT; i++) {
//                url = "http://" + HOST_NAME + ":"+ PORT +"/api/station";
////                url = "http://" + HOST_NAME + ":"+ PORT +"/services/station";
//                System.out.println("POST " + url);
//
////                AddStationMessage requestBody = new AddStationMessage(
////                        "station-"+(i+1), i+1,
////                        LocationDAO.FloorPlanImage.values()[random.nextInt(1, 3)],
////                        random.nextInt(-1000, 1000),
////                        random.nextInt(-1000, 1000)
////                );
//
//                AddStationMessage requestBody = new AddStationMessage(
//                        "station-"+(i+1), i+1,
//                        LocationDAO.FloorPlanImage.values()[i%3],
//                        i*50,
//                        0
//                );
//
//
////                System.out.println(requestBody);
//                String responseBodyStr = sendPostRequest(url, requestBody, "");
////                System.out.println(responseBodyStr);
//                AddStationResponse responseBody = (AddStationResponse) JsonSerializable.deserialize(responseBodyStr, AddStationResponse.class);
//
//                assert responseBody != null;
//                if (    ! responseBody.isNameOk()
//                        || ! responseBody.isNumberOk()
//                        || ! responseBody.isLocationOk()
//                        || !responseBody.isSuccess())
//                {
//                    Assertions.fail("adding station failed !");
//                }
//            }
//
//            // Benutzer einfügen
//            final int USER_COUNT = 3;
//            for (int i = 0; i < USER_COUNT; i++) {
//                url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
////                url = "http://" + HOST_NAME + ":"+ PORT +"/services/user";
//                System.out.println("POST " + url);
//                AddUserMessage requestBody = new AddUserMessage("testuser-"+(i+1), "0000-000"+(i+1));
////                System.out.println(requestBody);
//                String responseBody = sendPostRequest(url, requestBody, "");
////                System.out.println(responseBody);
//                AddUserResponse registerUserResponse = (AddUserResponse) JsonSerializable.deserialize(responseBody, AddUserResponse.class);
//                if ( ! registerUserResponse.isSuccess()) {
//                    Assertions.fail("adding user failed !");
//                }
//            }
//        }
//        catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//
//    }

//    @AfterAll
//    static void cleanUp() {
//        System.out.println("Cleanup after all tests.");
//        UserDAO.deleteAll();
//        StationDAO.deleteAll();
//    }

    @Test
    @Order(1)
    void testCheckStation() {
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station/check";
            System.out.println("PUT " + url);

            CheckStationMessage requestBody = new CheckStationMessage("1", "0000-0001");
            String responseBodyStr = sendPutRequest(url, requestBody,  "");
            System.out.println(responseBodyStr);

            CheckStationResponse responseBody = (CheckStationResponse) JsonSerializable.deserialize(responseBodyStr, CheckStationResponse.class);
            if ( ! responseBody.isStationNumberOk() || ! responseBody.isCardIdOk() || ! responseBody.isSuccess() ) {
                Assertions.fail("checking station failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

//    @Test
//    @Order(2)
//    void testCheckStationInvalidStationNumber() {
//        try {
//            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station/check";
//            System.out.println("DELETE " + url);
//
//            CheckStationMessage requestBody = new CheckStationMessage("-1", "0000-0000-0000-0001");
//            String responseBodyStr = sendPutRequest(url, requestBody,  "");
//            System.out.println(responseBodyStr);
//
//            CheckStationResponse responseBody = (CheckStationResponse) JsonSerializable.deserialize(responseBodyStr, CheckStationResponse.class);
//            if ( responseBody.isStationNumberOk() || ! responseBody.isCardIdOk() || responseBody.isSuccess() ) {
//                Assertions.fail("checking station succeeded despite invalid station number or wrong response message fields!");
//            }
//        }
//        catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//    }
//
//    @Test
//    @Order(3)
//    void testCheckStationInvalidCardId() {
//        try {
//            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station/check";
//            System.out.println("PUT " + url);
//
//            CheckStationMessage requestBody = new CheckStationMessage("1", "0000-0000-0000-0001####");
//            String responseBodyStr = sendPutRequest(url, requestBody,  "");
//            System.out.println(responseBodyStr);
//
//            CheckStationResponse responseBody = (CheckStationResponse) JsonSerializable.deserialize(responseBodyStr, CheckStationResponse.class);
//            if ( ! responseBody.isStationNumberOk() || responseBody.isCardIdOk() || responseBody.isSuccess() ) {
//                Assertions.fail("checking station succeeded despite invalid card id or wrong response message fields!");
//            }
//        }
//        catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//    }
//
//    @Test
//    @Order(4)
//    void testUncheckStationInvalidCardId() {
//        try {
//            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/station/uncheck";
//            System.out.println("PUT " + url);
//
//            CheckStationMessage requestBody = new CheckStationMessage("1", "0000-0000-0000-0001");
//            String responseBodyStr = sendPutRequest(url, requestBody,  "");
//            System.out.println(responseBodyStr);
//
//            CheckStationResponse responseBody = (CheckStationResponse) JsonSerializable.deserialize(responseBodyStr, CheckStationResponse.class);
//            if ( ! responseBody.isStationNumberOk() || ! responseBody.isCardIdOk() || ! responseBody.isSuccess() ) {
//                Assertions.fail("unchecking station failed !");
//            }
//        }
//        catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//    }


}
