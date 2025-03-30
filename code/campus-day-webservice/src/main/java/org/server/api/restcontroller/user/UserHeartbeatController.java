package org.server.api.restcontroller.user;

import org.server.api.restcontroller.AbstractController;
import org.server.api.restcontroller.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserHeartbeatController extends AbstractController {
    static class HeartbeatCounterHandlerThreat extends Thread {
        public HeartbeatCounterHandlerThreat() {}

        @Override
        public void run () {
            while (true) {
                for (String authToken : AbstractController.clients.keySet()) {

                    Client client = AbstractController.clients.get(authToken);

                    // falls Counter = 0 (seit 3s keine mehr gesendet), ausloggen aus Tabelle
                    if ( client.getSecondsSinceLastHeartbeatReceived() <= 0 ) {

                        // Client ausloggen
                        System.out.println("logging out client '" + client.getUsername() + "'" );
                        AbstractController.clients.remove(authToken);
                    }
                }
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class HeartbeatCounterReducerThreat extends Thread {
        public HeartbeatCounterReducerThreat() {}
        @Override
        public void run () {
            while (true) {
                for (Client client : AbstractController.clients.values()) {

                    // Counter senken
                    System.out.printf("Reducing heartbeat counter (%d) for user '%s'\n",
                            client.getSecondsSinceLastHeartbeatReceived(),
                            client.getUsername());

                    client.setSecondsSinceLastHeartbeatReceived(client.getSecondsSinceLastHeartbeatReceived()-1);
                }
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static {
        System.out.println("starting HeartbeatCounterHandlerThreat as daemon...");
        HeartbeatCounterHandlerThreat h = new HeartbeatCounterHandlerThreat();
        h.setDaemon(true);
        h.start();

        System.out.println("starting HeartbeatCounterReducerThreat as daemon...");
        HeartbeatCounterReducerThreat r = new HeartbeatCounterReducerThreat();
        r.setDaemon(true);
        r.start();
    }

    @GetMapping("user/heartbeat")
    String resetHeartbeatCounter(@RequestHeader("Authorization") String authToken) {

        // Counter zurücksetzten für User, wenn Heartbeat-Nachricht ankommt
        Client client = AbstractController.clients.get(authToken);

        System.out.printf("Resetting heartbeat counter (%d) for '%s'\n",
                client.getSecondsSinceLastHeartbeatReceived(),
                client.getUsername());
        client.setSecondsSinceLastHeartbeatReceived(3);

        return "OK";
    }
}
