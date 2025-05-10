package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserResponse extends JsonSerializable {
    private boolean deleted;
}
