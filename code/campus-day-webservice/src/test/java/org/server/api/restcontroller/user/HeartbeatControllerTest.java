package org.server.api.restcontroller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.server.api.JsonSerializable;
import org.server.api.messages.user.LoginMessage;
import org.server.api.messages.user.LoginUserResponse;

import static org.server.RestApiTestConfig.HOST_NAME;
import static org.server.RestApiTestConfig.PORT;
import static org.server.util.RestApiCalls.sendGetRequest;
import static org.server.util.RestApiCalls.sendPostRequest;

public class HeartbeatControllerTest {
    @Test
    void testLoadUser() {
        System.out.println("::::: user heartbeat message reception test :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user/auth";
            System.out.println("POST " + url);

            // login
            LoginMessage loginMessage = new LoginMessage("testuser1", "1");
            String response = sendPostRequest(url,loginMessage,"");
            System.out.println(response);

            // get auth header
            String authToken = ((LoginUserResponse) JsonSerializable.deserialize(response, LoginUserResponse.class)).getToken();

//            authToken = "Vhnq7MYZKeHRnbTxVziSG43J3o2CmuSG";

            url = "http://" + HOST_NAME + ":"+ PORT +"/api/user/heartbeat";
            System.out.println("GET " + url);

            // send get request with auth token in http auth header
            response = sendGetRequest(url, authToken);
            System.out.println(response);
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
