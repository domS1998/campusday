package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserResponse extends JsonSerializable {
    private boolean userExists;
    private boolean userValid;
    private boolean passwordValid;
    private boolean success;
}