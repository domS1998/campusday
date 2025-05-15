package org.server.api.messages.user;

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
public class UpdateUserMessage extends JsonSerializable {
    private String cardId;
    private String username;
}
