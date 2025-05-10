package org.server.api.restcontroller;

import org.server.api.messages.user.DeleteUserResponse;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("api/")
public class AbstractController {

}