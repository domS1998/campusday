package org.server.api.restcontroller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private final String username;
    private int secondsSinceLastHeartbeatReceived = 3;
    public Client(String username) {
        this.username = username;
    }
}
