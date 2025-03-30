package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserResponse extends JsonSerializable {
    private boolean loggedIn;
    private boolean usernameOK;
    private boolean passwordOK;
    private String token;
}