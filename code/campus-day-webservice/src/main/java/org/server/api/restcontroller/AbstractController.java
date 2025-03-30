package org.server.api.restcontroller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("api/")
public class AbstractController {
    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // Lookup Tabelle mit eingeloggten Token-Client Paaren
    public static final ConcurrentHashMap<String, Client> clients = new ConcurrentHashMap<String, Client>();

    // prüfen, ob bereits eingeloggt
    // genutzt, um login zu verifizieren
    protected boolean isLoggedIn(String username){

        // eingeloggt, falls Benutzername in Clienttabelle
        for (var client : clients.values()){
            if (client.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    // prüfen, ob Token gültig
    // genutzt, um alle APIs außer login zu authorisieren
    protected boolean isAuthorized (AccessToken token) {
        System.out.println(":::: AbstractController: attempting to authorize user with token '" + token.getVal() + "'");

        for (var x : clients.keySet()){
            System.out.println(x);
        }

        System.out.println(clients.containsKey(token.getVal()));

        return clients.containsKey(token.getVal());

    }

}