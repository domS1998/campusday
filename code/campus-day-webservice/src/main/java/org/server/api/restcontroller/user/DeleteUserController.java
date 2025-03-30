package org.server.api.restcontroller.user;

import org.server.api.messages.user.DeleteUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.api.restcontroller.AccessToken;
import org.server.exceptions.NoSuchUserException;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteUserController extends AbstractController {

    @DeleteMapping("/user")
    String deleteUser(@RequestHeader("Authorization") String authToken) {

        System.out.println(":::: DeleteUserController: attempting to unregister user with token " + authToken);

        AccessToken accessToken = new AccessToken(authToken);
        if ( ! isAuthorized(accessToken)) {
            return new DeleteUserResponse(false, false).toString();
        }

        // authorisiert, Benutzer löschen
        System.out.println("table["+ accessToken.getVal() +"] = " + clients.get(accessToken.getVal()) );

//        UserDAO user = new UserDAO(clients.get(accessToken.getVal()).getUsername());
//        try {
//            user.delete();
//        }
//        catch (NoSuchUserException e) {
//            return new DeleteUserResponse(true, false).toString();
//        }

        // Ausloggen nach Löschen
        clients.remove(accessToken.getVal());

        return new DeleteUserResponse(true, true).toString();
    }
}
