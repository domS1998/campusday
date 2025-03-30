package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserResponse extends JsonSerializable {
    private boolean usernameExits;
    private boolean cardIdExits;
    private boolean success;
}