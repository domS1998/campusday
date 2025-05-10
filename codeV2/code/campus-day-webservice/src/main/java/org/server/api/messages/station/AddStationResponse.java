package org.server.api.messages.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddStationResponse extends JsonSerializable {
    private boolean nameOk;
    private boolean numberOk;
    private boolean locationOk;
    private boolean success;
}
