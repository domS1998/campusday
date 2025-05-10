package org.server.api.messages.user;

import lombok.*;
import org.server.api.JsonSerializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserResponse extends JsonSerializable {
    private boolean authorized;
    private boolean updated;
}