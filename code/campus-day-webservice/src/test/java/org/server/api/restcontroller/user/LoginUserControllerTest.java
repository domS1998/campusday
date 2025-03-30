package org.server.api.restcontroller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.server.api.messages.user.LoginMessage;
import static org.server.util.RestApiCalls.sendPostRequest;
import static org.server.RestApiTestConfig.*;

public class LoginUserControllerTest {


    @Test
    void testLoginUser() {
        System.out.println("::::: login user test :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user/auth";
            System.out.println("POST " + url);
            LoginMessage loginMessage = new LoginMessage("testuser1", "1");
            System.out.println(loginMessage);
            String response = sendPostRequest(url, loginMessage, "");
            System.out.println(response);
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
