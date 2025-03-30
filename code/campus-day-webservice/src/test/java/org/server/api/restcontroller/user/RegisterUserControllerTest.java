package org.server.api.restcontroller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.server.api.JsonSerializable;
import org.server.api.messages.user.LoginMessage;
import org.server.api.messages.user.RegisterMessage;
import org.server.api.messages.user.RegisterUserResponse;

import static org.server.RestApiTestConfig.HOST_NAME;
import static org.server.RestApiTestConfig.PORT;
import static org.server.util.RestApiCalls.sendPostRequest;

public class RegisterUserControllerTest {

    @Test
    void testRegisterUser0() {
        System.out.println("::::: register user test 1 :::::");
        try {
            String url = "http://" + HOST_NAME + ":"+ PORT +"/api/user/signup";
            System.out.println("POST " + url);
            RegisterMessage registerMessage = new RegisterMessage("testuser1", "1");
            System.out.println(registerMessage);
            String response = sendPostRequest(url, registerMessage, "");
            System.out.println(response);

            RegisterUserResponse registerUserResponse = (RegisterUserResponse) JsonSerializable.deserialize(response, RegisterUserResponse.class);
            if ( ! registerUserResponse.isSuccess()) {
                Assertions.fail("registering user failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testRegisterUser1() {
        System.out.println("::::: register user test 2 :::::");
        try {
            String url = "http://" + HOST_NAME + ":" + PORT +"/api/user/signup";
            System.out.println("POST " + url);
            RegisterMessage registerMessage = new RegisterMessage("testuser2", "2");
            System.out.println(registerMessage);
            String response = sendPostRequest(url, registerMessage, "");
            System.out.println(response);

            RegisterUserResponse registerUserResponse = (RegisterUserResponse) JsonSerializable.deserialize(response, RegisterUserResponse.class);
            if ( ! registerUserResponse.isSuccess()) {
                Assertions.fail("registering user failed !");
            }
        }
        catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
