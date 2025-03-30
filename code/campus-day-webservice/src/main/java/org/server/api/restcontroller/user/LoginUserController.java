package org.server.api.restcontroller.user;

import org.server.api.messages.user.LoginMessage;
import org.server.api.messages.user.LoginUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.api.restcontroller.AccessToken;
import org.server.api.restcontroller.Client;
import org.server.exceptions.NoSuchUserException;
import org.server.exceptions.WrongPasswordException;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginUserController extends AbstractController {

    @PostMapping("user/auth")
    String loginUser(@RequestBody LoginMessage loginMessage) {

        System.out.println(":::: UserController: attempting to login user '"+loginMessage.getUsername()+"' with password '"+loginMessage.getPassword()+"'");

        // Prüfen, ob bereits eingeloggt
        if ( super.isLoggedIn(loginMessage.getUsername()) ) {
            return new LoginUserResponse(true, true, false, "").toString();
        }

        // Prüfen, ob Eingabedaten gültig sind

        UserDAO user = new UserDAO(loginMessage.getUsername(), loginMessage.getPassword());
        LoginUserResponse response;
//        try {
//            user.checkPassword();
//        }
//        catch (NoSuchUserException e) {
//            return new LoginUserResponse(false, false, true, "").toString();
//        }
//        catch (WrongPasswordException e) {
//            return new LoginUserResponse(false, true, false, "").toString();
//        }

        // Daten gültig, Access Token generieren und Benutzer in Tabelle eintragen
        AccessToken token = new AccessToken();
        clients.put(token.getVal(), new Client(loginMessage.getUsername()));

        // Benutzer ist eingeloggt, Anwort + Taken senden
        return new LoginUserResponse(true, true, true, token.getVal()).toString();
    }

    @RequestMapping("user/dump")
    String dumpClients() {
        StringBuilder result = new StringBuilder("Clients: \n");
        for (var e : clients.entrySet()){
            result.append("| ").append(e.getKey()).append(" | ").append(e.getValue().getUsername()).append(" |\n");
        }
        return result.toString();
    }

}