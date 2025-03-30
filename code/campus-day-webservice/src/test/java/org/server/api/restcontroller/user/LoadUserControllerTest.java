package org.server.api.restcontroller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.server.api.JsonSerializable;
import org.server.api.messages.user.LoginMessage;
import org.server.api.messages.user.LoginUserResponse;
import static org.server.util.RestApiCalls.sendGetRequest;
import static org.server.util.RestApiCalls.sendPostRequest;
import static org.server.RestApiTestConfig.*;

public class LoadUserControllerTest {

    @Test
    void testLoadUser() {
        System.out.println("::::: load user test :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user/auth";
            System.out.println("POST " + url);

            // login
            LoginMessage loginMessage = new LoginMessage("testuser1", "1");
            String response = sendPostRequest(url,loginMessage,"");
            System.out.println(response);

            // get auth header
            String authToken = ((LoginUserResponse) JsonSerializable.deserialize(response, LoginUserResponse.class)).getToken();

            url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
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
