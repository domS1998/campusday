package org.server.api.restcontroller.user;

import org.server.api.messages.user.LoadUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoadUserController extends AbstractController {

    @GetMapping("user/{cardId}")
    String loadUser(@PathVariable String cardId) {

        System.out.println(":::: LoadUserController: attempting to load user with card id '" + cardId+"'");

        UserDAO userDAO = UserDAO.findByRfid(cardId);
        if (userDAO == null) {
            return new LoadUserResponse(null).toString();
        }
//        try {
//            userDAO.loadByRfid(cardId);
//        }
        // null falls nicht vorhanden
//        catch (Exception e) {
//            return new LoadUserResponse(null).toString();
//        }


        return new LoadUserResponse(userDAO).toString();
    }
}
