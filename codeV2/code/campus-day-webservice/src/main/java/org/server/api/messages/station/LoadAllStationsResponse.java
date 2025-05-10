package org.server.api.messages.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.server.api.JsonSerializable;
import org.server.orm.classes.StationDAO;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadAllStationsResponse extends JsonSerializable {
    private ArrayList<StationDAO> stations;
}
