package org.server.api.restcontroller;

import java.security.SecureRandom;
import java.util.Base64;

// Token für Authentifizieren + Identifizieren eines eingeloggten Clients
public class AccessToken {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private final String val;

    public String getVal() {return this.val;}

    public AccessToken (String token) { this.val = token; }

    public AccessToken () {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        this.val = base64Encoder.encodeToString(randomBytes);
    }

}
