package org.server.api.restcontroller.station;

import org.server.api.messages.station.DeleteStationResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteStationController extends AbstractController {

    @DeleteMapping("station/{name}")
    public String deleteStation(@PathVariable String name) {

        System.out.println("attempting to delete station '" + name + "'...");

        StationDAO stationDAO = new StationDAO();
        stationDAO.load(name);
        try{
            stationDAO.delete();
        }
        catch(Exception e){
            System.out.println(e);
            return new DeleteStationResponse(false).toString();
        }
        return new DeleteStationResponse(true).toString();
    }

    @DeleteMapping("station/all")
    public String deleteAllStations() {

        try{
            StationDAO.deleteAll();
        }
        catch(Exception e){
            e.getMessage();
            return new DeleteStationResponse(false).toString();
        }
        return new DeleteStationResponse(true).toString();

    }
}
