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
public class UpdateStationResponse extends JsonSerializable {
    private boolean nameOk;
    private boolean numberOk;
    private boolean imageOk;
    private boolean xCoordinateOk;
    private boolean yCoordinateOk;
    private boolean success;
}