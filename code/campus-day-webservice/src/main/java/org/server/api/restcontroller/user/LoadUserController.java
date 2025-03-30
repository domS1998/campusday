package org.server.api.restcontroller.user;

import org.server.api.messages.user.LoadUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.api.restcontroller.AccessToken;
import org.server.api.restcontroller.Client;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoadUserController extends AbstractController {

    @GetMapping("user")
    String loadUser(@RequestHeader("Authorization") String authToken) {

        System.out.println(":::: LoadUserController: attempting to load user with token " + authToken);

        AccessToken accessToken = new AccessToken(authToken);
        if ( ! isAuthorized(accessToken)) {
            return new LoadUserResponse(false, null).toString();
        }

        // authorisiert, Benutzerdaten aus DB laden
        UserDAO user = new UserDAO();
        Client client = clients.get(accessToken.getVal());
        // Benutzer
        user.load(client.getUsername());
//        try {
//            // Chats
////            user.setChats(Chat.loadAll(client.getUsername()));
//            // Nachrichten für jeden Chat laden
////            for (Chat chat : user.getChats()) {
////                chat.setMessages(Message.loadAll(client.getUsername(), chat.getOtherUser(client.getUsername())));
////            }
//        }
//        catch (NoSuchChatException | ChatWithUserException | NoSuchUserException e) {
//            System.out.println(":::: LoadUserController: " + e.getMessage());
//        }
        return new LoadUserResponse(true, user).toString();
    }
}
