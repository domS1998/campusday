package org.server.api.restcontroller.user;

import jakarta.annotation.PostConstruct;
import org.server.api.messages.user.AddUserMessage;
import org.server.api.messages.user.AddUserResponse;
import org.server.api.restcontroller.AbstractController;
import org.server.orm.classes.StationDAO;
import org.server.orm.classes.UserDAO;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableWebSocket
@RestController
public class AddUserController extends AbstractController implements WebSocketConfigurer {

    @PostMapping("user")
    String addUser(@RequestBody AddUserMessage jsonMessage) {

        System.out.printf(":::: AddController: Attempting to add %s  with card id %s\n",
                jsonMessage.getUsername(), jsonMessage.getCardId());

        // Null Eingaben sind ungültig
        if (jsonMessage.getUsername() == null || jsonMessage.getCardId() == null) {
            return new AddUserResponse(false, false, false).toString();
        }

        // Leere Eingaben sind ungültig
        if (jsonMessage.getUsername().isEmpty() || jsonMessage.getCardId().isEmpty()) {
            return new AddUserResponse(false, false, false).toString();
        }

        // Falls Kartennummer schon einem Benutzer gehört
        UserDAO userLoaded = UserDAO.findByRfid(jsonMessage.getCardId());
        if (userLoaded != null) {
            return new AddUserResponse(false, true, false).toString();
        }

        System.out.println(userLoaded);

        userLoaded = new UserDAO();

        // Falls Benutzername schon existiert
        try {
            System.out.println(userLoaded);
            userLoaded.load(jsonMessage.getUsername());
            System.out.println(userLoaded);
            return new AddUserResponse(true, false, false).toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // ansonsten gültigen Benutzer speichern
        userLoaded = new UserDAO(jsonMessage.getUsername(), jsonMessage.getCardId());
        userLoaded.persist();

        // Benutzer mit allen vorhandenen Stationen verbinden
        ArrayList<StationDAO> allStations = StationDAO.loadAll();
        userLoaded.linkStations(allStations);
        userLoaded.merge();

        // Antwortnachricht für Erfolg zurückgeben
        return new AddUserResponse(false, false, true).toString();
    }

    private final AtomicReference<WebSocketSession> activeSession = new AtomicReference<>(null);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SimpleSocketHandler(), "/ws").setAllowedOrigins("*");
    }

    @PostConstruct
    public void init() {
        System.out.println("Waiting for UI to connect...");
    }

    @PostMapping("user/load")
    public ResponseEntity<String> sendMessageToWebSocket(@RequestBody String body) {
        WebSocketSession session = activeSession.get();
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(body));
                return ResponseEntity.ok("Message sent to UI");
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("Failed to send message: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(503).body("No UI connected");
        }
    }

    private class SimpleSocketHandler extends TextWebSocketHandler {

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            activeSession.set(session);
            System.out.println("UI connected");
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
            activeSession.set(null);
            System.out.println("UI disconnected, restarting WebSocket...");
            System.out.println("Waiting for UI to connect...");
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) {
            System.err.println("WebSocket error: " + exception.getMessage());
        }
    }

}
