package org.server.api.messages.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.server.api.JsonSerializable;
import org.server.orm.classes.LocationDAO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddStationMessage extends JsonSerializable {
    private String name;
    private int number;
    private LocationDAO.FloorPlanImage imageLabel;
    private int xCoordinate;
    private int yCoordinate;
}
