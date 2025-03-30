package org.server.api.restcontroller.user;

import org.server.api.messages.user.RegisterMessage;
import org.server.api.messages.user.RegisterUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.exceptions.DuplicateUserException;
import org.server.exceptions.SaveObjectException;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterUserController extends AbstractController {

    @PostMapping("user/signup")
    String registerUser(@RequestBody RegisterMessage jsonMessage) {

        System.out.printf(":::: RegisterController: Attempting to register %s  with password %s\n",
                jsonMessage.getUsername(), jsonMessage.getPassword());

        // Leere Eingaben sind ungültig
        if (jsonMessage.getUsername().isEmpty() || jsonMessage.getPassword().isEmpty()) {
            return new RegisterUserResponse(false, false, false, false).toString();
        }

        UserDAO user = new UserDAO(jsonMessage.getUsername(), jsonMessage.getPassword());
        RegisterUserResponse response = new RegisterUserResponse();
//        try {
//            user.insert();
//            response.setSuccess(true);
//            response.setPasswordValid(true);
//            response.setUserExists(false);
//            response.setUserValid(true);
//            return response.toString();
//        }
//        catch (SaveObjectException e) {
//            response.setPasswordValid(false);
//            response.setUserExists(false);
//            response.setUserValid(false);
//        }
//        catch (DuplicateUserException e) {
//            response.setPasswordValid(false);
//            response.setUserExists(true);
//            response.setUserValid(false);
//        }
        response.setSuccess(false);
        return response.toString();
    }
}
