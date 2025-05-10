package org.server.exceptions;

public class NoSuchUserException extends Exception{

    public NoSuchUserException(String username ) {
        super("User " + username +" does not exist in database!\n");
    }
}
