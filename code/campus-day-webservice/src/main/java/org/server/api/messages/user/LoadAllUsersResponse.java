package org.server.api.messages.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.server.api.JsonSerializable;
import org.server.orm.classes.UserDAO;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadAllUsersResponse extends JsonSerializable {
    ArrayList<UserDAO> users;
}
