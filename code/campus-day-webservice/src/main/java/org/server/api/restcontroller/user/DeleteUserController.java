package org.server.api.restcontroller.user;

import org.server.api.messages.user.DeleteUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.server.orm.classes.UserStationDAO;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
public class DeleteUserController extends AbstractController {

    // Benutzer löschen
    @DeleteMapping("user/{cardId}")
    String deleteUser(@PathVariable String cardId) {
        System.out.println(":::: DeleteUserController: attempting to delete user with card id '" + cardId + "'");

        // Prüfen auf korrekte Kartennummer
        UserDAO userLoaded = UserDAO.findByRfid(cardId);
        if (userLoaded == null) {
            // Erfolglos falls Fehler
            return new DeleteUserResponse(false).toString();
        }
//        try {
//            userDAO.loadByRfid(cardId);
//        }

//        catch (Exception e) {
//            e.printStackTrace();
//            return new DeleteUserResponse(false).toString();
//        }

        // Ansonsten Benutzer löschen und Erfolgsnachrichte zurückgeben


//        for (var userStation : userLoaded.getUserStations()) {
//            userStation.setUser(null);
//            userStation.getStation().getUserStations().remove(userStation);
//            userLoaded.getUserStations().remove(userStation);
//            userStation.setStation(null);
//            userStation.delete();
//        }
//        Iterator<UserStationDAO> iterator = userLoaded.getUserStations().iterator();
//        while (iterator.hasNext()) {
//            UserStationDAO userStation = iterator.next();
//            userStation.setUser(null);
//            userStation.getStation().getUserStations().remove(userStation);
//            iterator.remove();  // ✅ Safe removal
//            userStation.setStation(null);
//            userStation.delete();
//        }
        userLoaded.getUserStations().clear();
        userLoaded.merge();
        userLoaded.delete();

        return new DeleteUserResponse(true).toString();
    }

    // alle Benutzer löschen
    @DeleteMapping("/user/all")
    String deleteAllUser() {
        System.out.println(":::: DeleteUserController: attempting to delete all users...");
        try {
            UserDAO.deleteAll();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new DeleteUserResponse(false).toString();
        }
        return new DeleteUserResponse(true).toString();
    }

    // gesamtes System zurücksetzen
    @DeleteMapping("resetAll")
    public String resetAll(){
        // alle Benutzer löschen
        UserDAO.deleteAll();
        // alle Stationen löschen
        StationDAO.deleteAll();
        return new DeleteUserResponse(true).toString();
    }
}
