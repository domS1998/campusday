package org.server.api.messages.station;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckStationResponse extends JsonSerializable {
    private boolean cardIdOk;
    private boolean stationNumberOk;
    private boolean success;
}
