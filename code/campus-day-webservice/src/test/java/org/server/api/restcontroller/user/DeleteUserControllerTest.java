package org.server.api.restcontroller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.server.api.JsonSerializable;
import org.server.api.messages.user.LoginMessage;
import org.server.api.messages.user.LoginUserResponse;

import static org.server.RestApiTestConfig.HOST_NAME;
import static org.server.RestApiTestConfig.PORT;
import static org.server.util.RestApiCalls.sendDeleteRequest;
import static org.server.util.RestApiCalls.sendPostRequest;

public class DeleteUserControllerTest {

    @Test
    void testRegisterUser() {
        System.out.println("::::: delete user test :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user/auth";
            System.out.println("POST " + url);

            LoginMessage loginMessage = new LoginMessage("testuser2", "2");
            System.out.println(loginMessage);

            // login to get auth token for deleting user
            String response = sendPostRequest(url, loginMessage, "");
            System.out.println(response);

            // get auth token from login response
            LoginUserResponse loginUserResponse = (LoginUserResponse) JsonSerializable.deserialize(response, LoginUserResponse.class);

            url = "http://" + HOST_NAME + ":"+ PORT +"/api/user";
            System.out.println("DELETE " + url);

            // send delete request
            response = sendDeleteRequest(url, loginUserResponse.getToken());

            // confirm correct response
            System.out.println(response);
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
