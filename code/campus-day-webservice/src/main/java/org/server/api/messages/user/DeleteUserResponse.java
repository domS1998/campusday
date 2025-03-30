package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteUserResponse extends JsonSerializable {
    private boolean authorized;
    private boolean deleted;
}
