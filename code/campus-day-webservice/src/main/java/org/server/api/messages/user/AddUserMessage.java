package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserMessage extends JsonSerializable {
    private String username;
    private String cardId;
}