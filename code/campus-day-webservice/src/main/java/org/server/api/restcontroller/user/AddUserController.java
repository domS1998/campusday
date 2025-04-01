package org.server.api.restcontroller.user;

import org.server.api.messages.user.AddUserMessage;
import org.server.api.messages.user.AddUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
        UserDAO userLoaded = UserDAO.findByRfid(jsonMessage.getCardId());
        if (userLoaded != null) {
            return new AddUserResponse(false, true, false).toString();
        }
//        try {
//            userDAO.loadByRfid(jsonMessage.getCardId());
//        }
//        catch (Exception e) {}
//        if ( userDAO.getUsername() != null ) {
//            return new AddUserResponse(false, true, false).toString();
//        }


        System.out.println(userLoaded);

        userLoaded = new UserDAO();

        // Falls Benutzername schon existiert
        try {
            System.out.println(userLoaded);
            userLoaded.load(jsonMessage.getUsername());
            System.out.println(userLoaded);
            return new AddUserResponse(true, false, false).toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // ansonsten gültigen Benutzer speichern
        userLoaded = new UserDAO(jsonMessage.getUsername(), jsonMessage.getCardId());
        userLoaded.persist();

        // Benutzer mit allen vorhandenen Stationen verbinden
        ArrayList<StationDAO> allStations = StationDAO.loadAll();
        userLoaded.linkStations(allStations);
        userLoaded.merge();

        // Antwortnachricht für Erfolg zurückgeben
        return new AddUserResponse(false, false, true).toString();
    }
}
