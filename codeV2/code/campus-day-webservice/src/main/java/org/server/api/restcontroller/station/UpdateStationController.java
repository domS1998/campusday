package org.server.api.restcontroller.station;

import org.server.api.messages.station.CheckStationMessage;
import org.server.api.messages.station.CheckStationResponse;
import org.server.api.messages.station.UpdateStationMessage;
import org.server.api.messages.station.UpdateStationResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateStationController extends AbstractController {

    @PutMapping("station/check")
    public String checkStation(@RequestBody CheckStationMessage message) {

        // Station laden
        StationDAO stationLoaded = StationDAO.findByNumber(message.getStationNumber());
        if (stationLoaded == null) {
            // Fehlernachricht falls Stationsnummer keiner registrierten Station gehört
            return new CheckStationResponse(true, false, false).toString();
        }

        // Benutzer laden
        UserDAO userLoaded = UserDAO.findByRfid(message.getCardId());
        if (userLoaded == null) {
            // Fehlernachricht wenn Benutzer mit Kartennummer nicht im System
            return new CheckStationResponse(false, true, false).toString();
        }

        // Ansonsten Station abhaken
        userLoaded.checkStation(stationLoaded.getName());
        userLoaded.merge();
        return new CheckStationResponse(true, true, true).toString();
    }

    @PutMapping("station/uncheck")
    public String uncheckStation(@RequestBody CheckStationMessage message) {

        // Station laden
        StationDAO stationLoaded = StationDAO.findByNumber(message.getStationNumber());
        if (stationLoaded == null) {
            // Fehlernachricht falls Stationsnummer keiner registrierten Station gehört
            return new CheckStationResponse(true, false, false).toString();
        }

        // Benutzer laden
        UserDAO userLoaded = UserDAO.findByRfid(message.getCardId());
        if (userLoaded == null) {
            // Fehlernachricht wenn Benutzer mit Kartennummer nicht im System
            return new CheckStationResponse(false, true, false).toString();
        }

        // Ansonsten Station abhaken
        userLoaded.uncheckStation(stationLoaded.getName());
        userLoaded.merge();
        return new CheckStationResponse(true, true, true).toString();
    }

    @PutMapping("station")
    public String updateStation(@RequestBody UpdateStationMessage message) {

        System.out.printf(":::: AddController: Attempting to update station with values ('%s', '%s', '%s', '%s', '%s')\n",
                message.getName(),
                message.getNumber(),
                message.getImageLabel(),
                message.getXCoordinate(),
                message.getYCoordinate()
        );
        // Station laden
//        StationDAO stationLoaded = StationDAO.findByNumber(message.getNumber()+"");

        StationDAO stationLoaded = StationDAO.findByName(message.getName());

        if (stationLoaded == null) {
            // Fehlernachricht falls Stationsnummer keiner registrierten Station gehört
            return new UpdateStationResponse(false, true, true, true, true, false).toString();
        }

        // Falsche Eingaben abfangen

        // Update in DB
        stationLoaded.setName(message.getName());
        stationLoaded.setNumber(message.getNumber());
        stationLoaded.getLocation().setImage(message.getImageLabel());
        stationLoaded.getLocation().setXCoordinate(message.getXCoordinate());
        stationLoaded.getLocation().setYCoordinate(message.getYCoordinate());
        stationLoaded.merge();

        return new UpdateStationResponse(true, true, true, true, true, true).toString();
    }
}
