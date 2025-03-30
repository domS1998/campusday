package org.server.api.restcontroller.user;

import org.server.api.messages.user.AddUserMessage;
import org.server.api.messages.user.AddUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddUserController extends AbstractController {

    @PostMapping("user")
    String addUser(@RequestBody AddUserMessage jsonMessage) {

        System.out.printf(":::: AddController: Attempting to add %s  with card id %s\n",
                jsonMessage.getUsername(), jsonMessage.getCardId());

        // Null Eingaben sind ungültig
        if (jsonMessage.getUsername() == null || jsonMessage.getCardId() == null) {
            return new AddUserResponse(false, false, false).toString();
        }

        // Leere Eingaben sind ungültig
        if (jsonMessage.getUsername().isEmpty() || jsonMessage.getCardId().isEmpty()) {
            return new AddUserResponse(false, false, false).toString();
        }

        // Falls Kartennummer schon einem Benutzer gehört
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.loadByRfid(jsonMessage.getCardId());
        }
        catch (Exception e) {}
        if ( userDAO.getUsername() != null ) {
            return new AddUserResponse(false, true, false).toString();
        }

        System.out.println(userDAO);

        System.out.println("..... 1");
        // Falls Benutzername schon existiert
        try {
            System.out.println(userDAO);
            userDAO.load(jsonMessage.getUsername());
            System.out.println(userDAO);
            System.out.println("..... 2");
            return new AddUserResponse(true, false, false).toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("..... 3");
        }

        System.out.println("..... 5");

        // ansonsten gültige Daten speichern
        userDAO = new UserDAO(jsonMessage.getUsername(), jsonMessage.getCardId());
        userDAO.persist();

        // Antwortnachricht für Erfolg zurückgeben
        return new AddUserResponse(false, false, true).toString();
    }
}
