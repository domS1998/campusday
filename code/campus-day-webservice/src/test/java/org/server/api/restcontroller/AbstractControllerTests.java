package org.server.api.restcontroller;

import org.junit.jupiter.api.*;
import org.server.api.JsonSerializable;
import org.server.api.messages.station.AddStationMessage;
import org.server.api.messages.station.AddStationResponse;
import org.server.api.messages.user.AddUserMessage;
import org.server.api.messages.user.AddUserResponse;
import org.server.api.messages.user.DeleteUserResponse;
import org.server.orm.classes.LocationDAO;

import java.util.Random;

import static org.server.RestApiTestConfig.HOST_NAME;
import static org.server.RestApiTestConfig.PORT;
import static org.server.util.RestApiCalls.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AbstractControllerTests {

    @BeforeAll
    static void setUp() {
        System.out.println("Setup before all tests.");
        String url = "";
        try {

            // 5 Stationen einfügen
            final int STATION_COUNT = 5;
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
//                System.out.println(responseBodyStr);
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
            final int USER_COUNT = 3;
            for (int i = 0; i < USER_COUNT; i++) {
                url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
                System.out.println("POST " + url);
                AddUserMessage requestBody = new AddUserMessage("testuser-"+(i+1), "0000-0000-0000-000"+(i+1));
//                System.out.println(requestBody);
                String responseBody = sendPostRequest(url, requestBody, "");
//                System.out.println(responseBody);
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

    @AfterAll
    static void cleanUp() {
        System.out.println("Cleanup after all tests.");
    }

    @Test
    @Order(1)
    void testResetAll() {
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/resetAll";
            System.out.println("DELETE " + url);

            String responseBodyStr = sendDeleteRequest(url,"");
            System.out.println(responseBodyStr);

            DeleteUserResponse responseBody = (DeleteUserResponse) JsonSerializable.deserialize(responseBodyStr, DeleteUserResponse.class);
            if ( ! responseBody.isDeleted() ) {
                Assertions.fail("resetting system failed!");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
