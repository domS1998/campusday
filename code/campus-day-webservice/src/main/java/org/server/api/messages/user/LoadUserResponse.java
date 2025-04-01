package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;
import org.server.orm.classes.UserDAO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadUserResponse extends JsonSerializable {
    private UserDAO user;
}
