package org.server.api.restcontroller.user;

import org.server.api.messages.user.LoadAllUsersResponse;
import org.server.api.messages.user.LoadUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class LoadUserController extends AbstractController {

    @GetMapping("user/{cardId}")
    String loadUser(@PathVariable String cardId) {
        System.out.println(":::: LoadUserController: attempting to load user with card id '" + cardId+"'");

        UserDAO userDAO = UserDAO.findByRfid(cardId);
        if (userDAO == null) {
            return new LoadUserResponse(null).toString();
        }
        return new LoadUserResponse(userDAO).toString();
    }

    @GetMapping("user/all")
    String loadUser() {
        System.out.println(":::: LoadUserController: attempting to load all users :");

        ArrayList<UserDAO> users = UserDAO.loadAll();
        System.out.println(users);
        return new LoadAllUsersResponse(users).toString();
    }
}
