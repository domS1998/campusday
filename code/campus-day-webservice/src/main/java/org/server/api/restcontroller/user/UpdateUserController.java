package org.server.api.restcontroller.user;

import org.server.api.messages.user.UpdateUserMessage;
import org.server.api.messages.user.UpdateUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.UserDAO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserController extends AbstractController {

    @PutMapping("user")
    public String updateUser(@RequestBody UpdateUserMessage message) {

        System.out.printf(":::: AddController: Attempting to update user with values ('%s', '%s')\n",
                message.getCardId(),
                message.getUsername()
        );

        UserDAO userLoaded = UserDAO.findByRfid(message.getCardId());

        if (userLoaded == null) {
            // Fehlernachricht falls Kartennummer keiner registrierten Station gehört
            return new UpdateUserResponse(false, true, false).toString();
        }

        // Falsche Eingaben abfangen

        // Update in DB
        userLoaded.setUsername(message.getUsername());
        userLoaded.merge();

        return new UpdateUserResponse(true, true, true).toString();
    }
}
