package org.server.api.restcontroller.user;

import org.server.api.messages.station.LoadAllStationsResponse;
import org.server.api.messages.user.LoadAllUsersResponse;
import org.server.api.messages.user.LoadUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class LoadStationController extends AbstractController {

    @GetMapping("station/all")
    String loadUser() {
        System.out.println(":::: LoadStationController: attempting to load all stations :");

        ArrayList<StationDAO> stations = StationDAO.loadAll();
        System.out.println(stations);
        return new LoadAllStationsResponse(stations).toString();
    }
}
