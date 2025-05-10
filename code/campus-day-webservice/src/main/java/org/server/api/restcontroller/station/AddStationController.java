package org.server.api.restcontroller.station;

import org.server.api.messages.station.AddStationMessage;
import org.server.api.messages.station.AddStationResponse;
import org.server.api.messages.user.AddUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AddStationController extends AbstractController {

    @PostMapping("station")
    public String addStation(@RequestBody AddStationMessage message) {

        System.out.printf(":::: AddController: Attempting to add station ('%s', '%s', '%s', '%s', '%s')\n",
                message.getName(),
                message.getNumber(),
                message.getImageLabel(),
                message.getXCoordinate(),
                message.getYCoordinate()
        );

        // Null Eingaben sind ungültig
        if (    message.getName()        == null
             //|| message.getNumber()      == null
             || message.getImageLabel()  == null
             //|| message.getXCoordinate() == null
             //|| message.getYCoordinate() == null
        ) {
            return new AddStationResponse(false, false, false, false).toString();
        }

        // Leere Eingaben sind ungültig
        if (    message.getName().isEmpty()
                //|| message.getNumber().isEmpty()
                //|| message.getImageLabel().isEmpty()
                //|| message.getXCoordinate().isEmpty()
                //|| message.getYCoordinate().isEmpty()
        ) {
            return new AddUserResponse(false, false, false).toString();
        }

        // Falls Stationsname schon vergeben
        StationDAO stationDAO = new StationDAO();
        // find by name query
        StationDAO stationLoaded = StationDAO.findByName(message.getName());

        // Falls ein Ergebnis, ist Name schon vergeben
        if (stationLoaded != null) {
            return new AddStationResponse(false, true, true, false).toString();
        }

        // Falls Nummer schon vergeben
        stationLoaded = StationDAO.findByNumber(String.valueOf(message.getNumber()));

        // Falls ein Ergebnis, ist Name schon vergeben
        if (stationLoaded != null) {
            return new AddStationResponse(true, false, true, false).toString();
        }

        // Location Label prüfen
        try {
            //LocationDAO.FloorPlanImage.valueOf(message.getImageLabel());
        }
        // Fehler, falls ungültiger Typ
        catch (Exception e) {
            e.printStackTrace();
            return new AddStationResponse(true, true, false, false).toString();
        }

        // ansonsten gültige Station speichern
        stationDAO = new StationDAO(
                message.getName(),
                message.getNumber(),
                message.getImageLabel(),
                message.getXCoordinate(),
                message.getYCoordinate()
        );
        stationDAO.persist();

        // Station mit allen vorhandenen Benutzern verbinden
        ArrayList<UserDAO> allUsers = UserDAO.loadAll();
        for (UserDAO user : allUsers) {
            user.linkStation(stationDAO);
            user.merge();
        }

        // Antwortnachricht für Erfolg zurückgeben
        return new AddStationResponse(true, true, true, true).toString();
    }
}
